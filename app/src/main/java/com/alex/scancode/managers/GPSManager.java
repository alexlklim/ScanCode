package com.alex.scancode.managers;

import android.location.Location;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GPSManager {
    private static final String TAG = "GPSManager";

    public static String convertGpsToString(Location gps) {
        Double longitude = gps.getLongitude();
        Double latitude = gps.getLatitude();
        List<String> gpsList = new ArrayList<>(Arrays.asList(String.valueOf(longitude), String.valueOf(latitude)));
        if (!gpsList.isEmpty()) {
            return TextUtils.join(",", gpsList);
        }
        return "none";
    }
}
