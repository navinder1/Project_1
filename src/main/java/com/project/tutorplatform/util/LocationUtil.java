package com.project.tutorplatform.util;

public class LocationUtil {

    public static boolean isValidLatitude(double lat) {
        return lat >= -90 && lat <= 90;
    }

    public static boolean isValidLongitude(double lon) {
        return lon >= -180 && lon <= 180;
    }

    public static boolean isValidCoordinates(double lat, double lon) {
        return isValidLatitude(lat) && isValidLongitude(lon);
    }

    public static String buildAddress(String line1, String city, String state) {
        StringBuilder sb = new StringBuilder();

        if (line1 != null) sb.append(line1).append(", ");
        if (city != null) sb.append(city).append(", ");
        if (state != null) sb.append(state);

        return sb.toString().replaceAll(", $", "");
    }
}