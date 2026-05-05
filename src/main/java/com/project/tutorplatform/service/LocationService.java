package com.project.tutorplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Location;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public Location getById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
    }

    public List<Location> getByCity(String city) {
        return locationRepository.findByCityIgnoreCase(city);
    }

    public void delete(Long id) {
        Location loc = getById(id);
        locationRepository.delete(loc);
    }
}