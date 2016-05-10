package com.places.infrastructure.service;

/**
 * Google Places API "status" replies.
 */
public interface Statuses {

    String STATUS_OK = "OK";

    String STATUS_ZERO_RESULTS = "ZERO_RESULTS";

    String STATUS_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";

    String STATUS_REQUEST_DENIED = "REQUEST_DENIED";

    String STATUS_INVALID_REQUEST = "INVALID_REQUEST";

}
