package com.places.application;

import com.places.domain.Place;
import com.places.domain.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FindMePlacesService {

    private final PlacesRepository placesRepository;

    @Autowired
    public FindMePlacesService(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public Optional<List<Place>> getPlaces(double latitude, double longitude) {
        return placesRepository.getNearbyPlaces(latitude, longitude);
    }
}