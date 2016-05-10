package com.places.errorhandling;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static com.places.infrastructure.service.Statuses.*;

/**
 * Represents an errorhandling or error thrown by Google Places API
 */
public class GooglePlacesException extends RuntimeException {
    private static final Map<String, Class<?>> statusClassMap = new HashMap<>();

    static {
        statusClassMap.put(STATUS_OK, null);
        statusClassMap.put(STATUS_ZERO_RESULTS, NoResultsFoundException.class);
        statusClassMap.put(STATUS_OVER_QUERY_LIMIT, OverQueryLimitException.class);
        statusClassMap.put(STATUS_REQUEST_DENIED, RequestDeniedException.class);
        statusClassMap.put(STATUS_INVALID_REQUEST, InvalidRequestException.class);
    }

    private String statusCode;
    private String errorMessage;


    public GooglePlacesException(String statusCode, String errorMessage) {
        super(statusCode + (errorMessage == null ? "" : ": \"" + errorMessage + "\""));
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }


    public GooglePlacesException(String statusCode) {
        this(statusCode, null);
    }


    public GooglePlacesException(Throwable t) {
        super(t);
    }


    public String getStatusCode() {
        return statusCode;
    }


    public String getErrorMessage() {
        return errorMessage;
    }


    public static GooglePlacesException parse(String statusCode, String errorMessage) {
        Class<?> errorClass = statusClassMap.get(statusCode);
        if (errorClass == null)
            return null;
        try {
            if (errorMessage == null || errorMessage.isEmpty())
                return (GooglePlacesException) errorClass.newInstance();
            else {
                Constructor<?> constructor = errorClass.getConstructor(String.class);
                return (GooglePlacesException) constructor.newInstance(errorMessage);
            }
        } catch (Exception e) {
            // Should never happen!
            throw new GooglePlacesException(e);
        }
    }
}
