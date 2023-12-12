package com.alex.scancode.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkManager {
    // Method to check if the device has an active internet connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public static boolean isServerAvailable(String serverUrl) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(serverUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Set your desired timeout in milliseconds

            int responseCode = connection.getResponseCode();

            // Check if the response code indicates success (2xx)
            return responseCode >= 200 && responseCode < 300;

        } catch (IOException e) {
            // An error occurred (e.g., connection timeout)
            return false;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
