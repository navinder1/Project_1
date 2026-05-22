package com.project.tutorplatform.mapper;

import com.project.tutorplatform.dto.response.LocationResponse;
import com.project.tutorplatform.entity.Location;

public class LocationMapper {

    public static LocationResponse toResponse(Location location) {

        if (location == null) return null;

        LocationResponse res = new LocationResponse();

        res.setId(location.getId());
        res.setLatitude(location.getLatitude());
        res.setLongitude(location.getLongitude());
        res.setAddressLine1(location.getAddressLine1());
        res.setAddressLine2(location.getAddressLine2());
        res.setCity(location.getCity());
        res.setState(location.getState());
        res.setPincode(location.getPincode());
        res.setCountry(location.getCountry());
        res.setFormattedAddress(location.getFormattedAddress());

        return res;
    }
}