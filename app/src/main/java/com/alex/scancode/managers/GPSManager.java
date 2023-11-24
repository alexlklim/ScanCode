package com.alex.scancode.managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.alex.scancode.activities.ScanActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GPSManager {
    private static final String TAG = "GPSManager";

    private static Location currentLocation;
    public static String gpsString;

    private static String convertGpsToString(List<String> gps) {
        Log.d(TAG, "convertGpsToString");
        if (gps != null && !gps.isEmpty()) {
            return TextUtils.join(",", gps);
        }
        return "";
    }


    public static String getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation");
        Double longitude = currentLocation.getLongitude();
        Double latitude = currentLocation.getLatitude();
        gpsString = convertGpsToString(new ArrayList<>(
                Arrays.asList(String.valueOf(longitude), String.valueOf(latitude))));
        return gpsString;
    }
}
