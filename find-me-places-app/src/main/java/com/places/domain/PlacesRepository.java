package com.places.domain;


import java.util.List;
import java.util.Optional;

public interface PlacesRepository {
    int DEFAULT_RADIUS = 500;
    int MAXIMUM_RESULTS = 20;
    String STRING_STATUS = "status";
    String STRING_ERROR_MESSAGE = "error_message";
    String STATUS_ZERO_RESULTS = "ZERO_RESULTS";
    String ARRAY_RESULTS = "results";
    String ARRAY_TYPES = "types";
    String STRING_PLACE_ID = "place_id";
    String STRING_NAME = "name";
    String STRING_VICINITY = "vicinity";

    Optional<List<Place>> getNearbyPlaces(double latitude, double longitude);
}

