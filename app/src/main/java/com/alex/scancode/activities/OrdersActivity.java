package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.alex.scancode.R;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private static final String TAG = "OrdersActivity";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        context = getApplicationContext();

        // try to sent not synch orders to server if server configured
        // if press redClose on special order It will try to synch

        // dont show synchButton if serverIsNotConfigured
        // if click order it opens specialOrderActivity




    }


    private List<Order> getOrdersFromDB(){

    }
}