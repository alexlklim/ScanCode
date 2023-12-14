package com.alex.scancode.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alex.scancode.R;
import com.alex.scancode.db.RoomDB;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;
import com.alex.scancode.models.json.OrderWithCodes;
import com.alex.scancode.models.json.OrdersList;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SynchManager {
    private static final String TAG = "SynchManager";

    private static Context context;
    RoomDB roomDB;
    SettingsManager settingsManager;
    URL url;

    public SynchManager(Context context) {
        SynchManager.context = context;
    }

    public CompletableFuture<Boolean> syncOrderWithServer(Order order) {
        Log.d(TAG, "syncOrderWithServer: " + order.getOrderNumber());
        Ans.showToast(context.getString(R.string.toast_try_to_synch_with_server), context);

        if (!checkCommon()) return null;
        settingsManager = new SettingsManager(context);
        // create json file order with codes
        roomDB = RoomDB.getInstance(context);
        List<Code> codeList = roomDB.codeDAO().getAllByOrderID(order.getId());
        OrderWithCodes orderWithCodes = new OrderWithCodes(order, codeList, settingsManager.getIdentifier());
        String json = orderWithCodes.toJson();

        CompletableFuture<Boolean> resultFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                url = new URL(settingsManager.getServerAddress());

                // Create a HttpURLConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                // Write the JSON data to the server
                try (OutputStream outputStream = urlConnection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                    writer.write(json);
                    writer.flush();
                }

                // Get the response from the server (optional, if needed)
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    resultFuture.complete(true);
                } else {
                    Log.e(TAG, "doInBackground: toast_server_is_not_available");
                    Ans.showToast(context.getString(R.string.toast_server_is_not_available), context);

                    resultFuture.complete(false);
                }

                // Disconnect the HttpURLConnection
                urlConnection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                resultFuture.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            resultFuture.complete(false);
            return null;
        });
        return resultFuture;
    }



    public void clearSynchOrders() {
        Toast.makeText(context, "Clear synchronized orders", Toast.LENGTH_SHORT).show();
//        AnswerManager.showToast(context.getString(R.string.toast_something_wrong_with_server), context);
        settingsManager = new SettingsManager(context);
        boolean answer = checkCommon();


        // handle special cases
        // checkCommon();
        // 1 all codes synchronized -> clear all
        // 2 all orders not synchronized -> do nothing
        // 3 show numbers of orders which was deleted


    }

    public boolean synchNotSynchOrders() {
        Toast.makeText(context, "Synch not synch orders", Toast.LENGTH_SHORT).show();

        List<OrderWithCodes> orderWithCodesList = getNotSynchOrdersWithCodes();
        for (OrderWithCodes orderWithCodes: orderWithCodesList){
            Order order = roomDB.orderDAO().getOrderByOrderNumber(orderWithCodes.getOrder().getOrderNumber());
            if (syncOrderWithServer(order) == null){
                return false;
            }
        }
        return true;

    }

    public OrdersList getNotSynchOrders(){
        settingsManager = new SettingsManager(context);
        OrdersList ordersList = new OrdersList(settingsManager.getIdentifier(), getNotSynchOrdersWithCodes());
        return ordersList;
    }

    public List<OrderWithCodes> getNotSynchOrdersWithCodes(){
        settingsManager = new SettingsManager(context);
        roomDB = RoomDB.getInstance(context);
        List<OrderWithCodes> mainList = new ArrayList<>();
        List<Order> orderListNotSynch = roomDB.orderDAO().getNonSynchOrders(0);

        for (Order order: orderListNotSynch){
            mainList.add(new OrderWithCodes(order, roomDB.codeDAO().getAllByOrderID(order.getId()), settingsManager.getIdentifier()));
        }
        return mainList;
    }



    public boolean checkIfOrderSynch(Order order){
        if (order.getIsSynch() == 0){
            return false;
        } else {
            Ans.showToast(context.getString(R.string.toast_order_already_synch), context);
            return true;
        }
    }




    public boolean checkCommon() {
        Log.d(TAG, "checkCommon: ");
        settingsManager = new SettingsManager(context);// 1 server is not configured
        if(!settingsManager.isServerConfigured()){
            Log.e(TAG, "checkCommon: toast_server_is_not_configured");
            Ans.showToast(context.getString(R.string.toast_server_is_not_configured), context);
            return false;
        }

        // 2 no internet connection
        if (!NetworkManager.isNetworkAvailable(context)){
            Log.e(TAG, "checkCommon: toast_network_is_not_available");
            Ans.showToast(context.getString(R.string.toast_network_is_not_available), context);
            return false;
        }
        return true;
    }
}
