package com.alex.scancode.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SynchManager {
    private static Context context;

    // get orders with isSynch=0
    // add indetificator for them


    public  SynchManager(Context context) {
        SynchManager.context = context;
    }

    public void syncOrdersWithServer() {
        Toast.makeText(context, "Synchronization with server", Toast.LENGTH_SHORT).show();
        // logic will be here

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

}
