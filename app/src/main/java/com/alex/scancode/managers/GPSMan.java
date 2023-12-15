package com.alex.scancode.managers;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GPSMan {
    private static final String TAG = "GPSManager";
    static SettingsMan sm;

    public static String convertGpsToString(Location gps, Context context) {
        Log.i(TAG, "convertGpsToString: ");
        sm = new SettingsMan(context);


        if (!sm.isAddLocationToCode()) {
            return "none";
        }


        Double longitude = gps.getLongitude();
        Double latitude = gps.getLatitude();
        List<String> gpsList = new ArrayList<>(Arrays.asList(String.valueOf(longitude), String.valueOf(latitude)));
        if (!gpsList.isEmpty()) {
            return TextUtils.join(",", gpsList);
        }
        return "none";
    }

}
