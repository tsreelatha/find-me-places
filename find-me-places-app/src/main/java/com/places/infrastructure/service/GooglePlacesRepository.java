package com.places.infrastructure.service;


import com.places.configuration.PlacesConfiguration;
import com.places.domain.Place;
import com.places.domain.PlacesRepository;
import com.places.errorhandling.GooglePlacesException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GooglePlacesRepository implements PlacesRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String ENCODING_TYPE = "UTF-8";
    private RestTemplate restTemplate;
    private PlacesConfiguration placesConfiguration;

    @Autowired
    public GooglePlacesRepository(PlacesConfiguration placesConfiguration) {
        this.placesConfiguration = placesConfiguration;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Optional<List<Place>> getNearbyPlaces(double latitude, double longitude) {
        URI targetUrl;
        try {
            targetUrl = UriComponentsBuilder.fromUriString(placesConfiguration.getUrl())
                    .queryParam("location", latitude + "," + longitude)
                    .queryParam("radius", DEFAULT_RADIUS)
                    .queryParam("key", placesConfiguration.getKey())
                    .build()
                    .encode(ENCODING_TYPE)
                    .toUri();
        } catch (UnsupportedEncodingException e) {

            throw new IllegalArgumentException("Could not encode request");
        }

        String response = restTemplate.getForObject(targetUrl, String.class);
        try {
            return translateToResponseObject(response);
        }catch (GooglePlacesException e){
            logger.error("Got exception while getting nearby places,{}", e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<List<Place>> translateToResponseObject(String response) {

        JSONObject json = new JSONObject(response);

        String statusCode = json.getString(STRING_STATUS);

        checkStatus(statusCode, json.optString(STRING_ERROR_MESSAGE));
        if (statusCode.equals(STATUS_ZERO_RESULTS))
            return Optional.empty();

        JSONArray results = json.getJSONArray(ARRAY_RESULTS);
        return parseResults(results, MAXIMUM_RESULTS);
    }


    private Optional<List<Place>> parseResults(JSONArray results, int limit) {
        List<Place> places = new ArrayList<>();

        for (int i = 0; i < limit; i++) {

            if (i >= results.length())
                return Optional.empty();

            JSONObject result = results.getJSONObject(i);
            // location

            String placeId = result.getString(STRING_PLACE_ID);
            String name = result.optString(STRING_NAME);
            String vicinity = result.optString(STRING_VICINITY, null);

            // the place "types"
            List<String> typesList = new ArrayList<>();
            JSONArray jsonTypes = result.optJSONArray(ARRAY_TYPES);
            if (jsonTypes != null) {
                for (int a = 0; a < jsonTypes.length(); a++) {
                    typesList.add(jsonTypes.getString(a));
                }
            }

            String types = "";

            for (String s : typesList)
            {
                types += s + ",";
            }
            Place place = new Place();

            // build a place object
            place.setName(name);
            place.setPlaceId(placeId);
            place.setVicinity(vicinity);
            place.setTypes(types);
            places.add(place);
        }
        return Optional.of(places);
    }

    protected static void checkStatus(String statusCode, String errorMessage) {
        GooglePlacesException e = GooglePlacesException.parse(statusCode, errorMessage);
        if (e != null)
            throw e;
    }

}
