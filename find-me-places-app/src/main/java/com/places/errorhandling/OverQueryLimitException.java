package com.places.errorhandling;


import com.places.infrastructure.service.Statuses;

public class OverQueryLimitException extends GooglePlacesException {
    public OverQueryLimitException(String errorMessage) {
        super(Statuses.STATUS_OVER_QUERY_LIMIT, errorMessage);
    }

    public OverQueryLimitException() {
        this(null);
    }
}
