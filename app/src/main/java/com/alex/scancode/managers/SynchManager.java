package com.alex.scancode.managers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alex.scancode.db.RoomDB;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;
import com.alex.scancode.models.json.OrderWithCodes;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SynchManager {
    private static Context context;

    // get orders with isSynch=0
    // add indetificator for them


    public  SynchManager(Context context) {
        SynchManager.context = context;
    }

    public void syncOrdersWithServer(Order order) {
        Toast.makeText(context, "Synchronization with server", Toast.LENGTH_SHORT).show();
        // logic will be here

        SettingsManager settingsManager = new SettingsManager(context);
        // create json file order with codes
        RoomDB roomDB = RoomDB.getInstance(context);
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


        // handle special cases
        // checkCommon();
        // 1 all codes synchronized -> clear all
        // 2 all orders not synchronized -> do nothing
        // 3 show numbers of orders which was deleted


    }
    public void checkCommon() {
        // 1 server is not configured
        // 2 no internet connection
        // 3 server is not found or answer
        // 4 something wrong
    }

    public void synchAllOrders() {
    }
}
