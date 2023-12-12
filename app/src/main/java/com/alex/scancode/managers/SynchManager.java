package com.alex.scancode.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alex.scancode.R;
import com.alex.scancode.db.RoomDB;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;
import com.alex.scancode.models.json.OrderWithCodes;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SynchManager {
    private static final String TAG = "SynchManager";

    private static Context context;
    RoomDB roomDB;
    SettingsManager settingsManager;

    // get orders with isSynch=0
    // add indetificator for them


    public  SynchManager(Context context) {
        SynchManager.context = context;
    }

    public void syncOrderWithServer(Order order) {
        Log.d(TAG, "syncOrderWithServer: ");
        Toast.makeText(context, context.getString(R.string.toast_try_to_synch_with_server), Toast.LENGTH_SHORT).show();
        settingsManager = new SettingsManager(context);

        // logic will be here
        boolean answer = checkCommon();

        // create json file order with codes
        roomDB = RoomDB.getInstance(context);
        List<Code> codeList = roomDB.codeDAO().getAllByOrderID(order.getId());
        OrderWithCodes orderWithCodes = new OrderWithCodes(order, codeList, settingsManager.getIdentifier());
        String json = orderWithCodes.toJson();

        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    // Create a URL object with the server endpoint
                    URL url = new URL("http://10.1.2.75:8080/server");

                    // Create a HttpURLConnection
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);

                    // Write the JSON data to the server
                    OutputStream outputStream = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write(params[0]);
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    // Get the response from the server (optional, if needed)
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Server request was successful
                        // You can handle the response here if needed
                    } else {
                        // Handle error cases
                    }

                    // Disconnect the HttpURLConnection
                    urlConnection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Handle the result if needed
            }
        }.execute(json);



        // add Toast for special cases like
        // checkCommon();
        // 1 all orders synchronized successfully
        // 2 all orders already synchronized

    }

    public void clearSynchOrders() {
        Toast.makeText(context, "Clear synchronized orders", Toast.LENGTH_SHORT).show();
        boolean answer = checkCommon();


        // handle special cases
        // checkCommon();
        // 1 all codes synchronized -> clear all
        // 2 all orders not synchronized -> do nothing
        // 3 show numbers of orders which was deleted


    }

    public void synchAllOrders() {
        Toast.makeText(context, "Clear synchronized orders", Toast.LENGTH_SHORT).show();
        boolean answer = checkCommon();


    }




    public boolean checkCommon() {
        // 1 server is not configured
        if(!settingsManager.isServerConfigured()){
            Log.e(TAG, "checkCommon: toast_server_is_not_configured");
            Toast.makeText(context, context.getString(R.string.toast_server_is_not_configured), Toast.LENGTH_SHORT).show();
            return false;
        }

        // 2 no internet connection
        if (NetworkManager.isNetworkAvailable(context)){
            Log.e(TAG, "checkCommon: toast_network_is_not_available");
            Toast.makeText(context, context.getString(R.string.toast_network_is_not_available), Toast.LENGTH_SHORT).show();
            return false;
        }

        // 3 server is not found or answer
        if (NetworkManager.isServerAvailable(settingsManager.getServerAddress())) {
            Log.e(TAG, "checkCommon: toast_server_is_not_available");
            Toast.makeText(context, context.getString(R.string.toast_server_is_not_available), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
