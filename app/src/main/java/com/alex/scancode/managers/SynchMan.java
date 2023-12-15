package com.alex.scancode.managers;

import android.content.Context;
import android.util.Log;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SynchMan {
    private static final String TAG = "SynchManager";

    private static Context context;
    RoomDB roomDB;
    SettingsMan sm;
    URL url;

    public SynchMan(Context context) {
        SynchMan.context = context;
    }

    public CompletableFuture<Boolean> syncOrderWithServer(Order order) {
        Log.d(TAG, "syncOrderWithServer: " + order.getOrderNumber());
        Ans.showToast(context.getString(R.string.toast_try_to_synch_with_server), context);

        if (!checkCommon()) return null;


        sm = new SettingsMan(context);
        // create json file order with codes
        roomDB = RoomDB.getInstance(context);
        List<Code> codeList = roomDB.codeDAO().getAllByOrderID(order.getId());
        OrderWithCodes orderWithCodes = new OrderWithCodes(order, codeList, sm.getIdentifier());
        String json = orderWithCodes.toJson();

        CompletableFuture<Boolean> resultFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                url = new URL(sm.getServerAddress());

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
        Log.d(TAG, "clearSynchOrders: ");
        Ans.showToast(context.getString(R.string.toast_clear_synch_orders), context);

        roomDB = RoomDB.getInstance(context);
        roomDB.orderDAO().deleteAllSynchOrders();

    }

    public boolean synchNotSynchOrders() {
        Ans.showToast(context.getString(R.string.toast_server_is_not_available), context);



        List<OrderWithCodes> orderWithCodesList = getNotSynchOrdersWithCodes();
        for (OrderWithCodes orderWithCodes: orderWithCodesList){
            Order order = roomDB.orderDAO().getOrderByOrderNumber(orderWithCodes.getOrder().getOrderNumber());
            if (syncOrderWithServer(order) == null){
                return false;
            }
        }
        return true;

    }


    public List<OrderWithCodes> getNotSynchOrdersWithCodes(){
        sm = new SettingsMan(context);
        roomDB = RoomDB.getInstance(context);
        List<OrderWithCodes> mainList = new ArrayList<>();
        List<Order> orderListNotSynch = roomDB.orderDAO().getNonSynchOrders();

        for (Order order: orderListNotSynch){
            mainList.add(new OrderWithCodes(order, roomDB.codeDAO().getAllByOrderID(order.getId()), sm.getIdentifier()));
        }
        return mainList;
    }

    public boolean checkCommon() {
        Log.d(TAG, "checkCommon: ");
        sm = new SettingsMan(context);// 1 server is not configured
        if(!sm.isServerConfigured()){
            Log.e(TAG, "checkCommon: toast_server_is_not_configured");
            Ans.showToast(context.getString(R.string.toast_server_is_not_configured), context);
            return false;
        }

        // 2 no internet connection
        if (!NetworkMan.isNetworkAvailable(context)){
            Log.e(TAG, "checkCommon: toast_network_is_not_available");
            Ans.showToast(context.getString(R.string.toast_network_is_not_available), context);
            return false;
        }
        return true;
    }
}
