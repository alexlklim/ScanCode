package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alex.scancode.R;
import com.alex.scancode.managers.adapters.CodeAdapter;
import com.alex.scancode.managers.databases.DBCodeManager;
import com.alex.scancode.managers.databases.DBOrderManager;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Profile;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {
    private static final String TAG = "ScanActivity";
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private RecyclerView recyclerView;
    private CodeAdapter codeAdapter;
    private Button scan_btn_do_finish_order;
    private DBCodeManager dbCodeManager;
    private DBOrderManager dbOrderManager;

    private List<Code> codeList = new LinkedList<>();
    private String orderNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);


        // initialize recyclerView to show data
        recyclerView = findViewById(R.id.recyclerView);
        codeAdapter = new CodeAdapter(codeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(codeAdapter);


        scan_btn_do_finish_order = findViewById(R.id.scan_btn_do_finish_order);
        scan_btn_do_finish_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: scan_btn_do_finish_order was pressed");
                saveNewOrder();

                // open alert dialog with ": OrderSaved and show button "Come back to main menu"
                // save all codes from list to DB
            }
        });
    }

    private void saveNewOrder() {
        Log.i(TAG, "saveNewOrder: ");
        dbOrderManager = new DBOrderManager(ScanActivity.this);
        Intent intent = getIntent();
        if (intent != null) {
            orderNumber = intent.getStringExtra("ORDER_NUMBER");
        } else {
            Log.d(TAG, "saveNewOrder: INTENT IS NULL");
        }
        dbOrderManager.addOrderToDB(orderNumber);
        Cursor cursor = dbOrderManager.getOrderIdByOrderNumber(orderNumber);
        if (cursor == null){
            Log.d(TAG, "saveNewOrder: CURSOR HAS NO DATA");
        } else {
            Log.d(TAG, "saveNewOrder: LLLLLLLLLLLLLLLLL");
            cursor.moveToFirst();
            cursor = dbOrderManager.getOrderIdByOrderNumber(orderNumber);
            int id = -1;

            if (cursor != null && cursor.moveToFirst()) {
                Log.i(TAG, "saveNewOrder: CURSOR HAS NO DATA");
                // Get the order ID from the cursor
                id =  cursor.getInt(0);
                saveNewCodesToDB(id);
            }
        }
    }

    private void saveNewCodesToDB(int orderNum) {
        Log.d(TAG, "saveNewCodesToDB: " + orderNum);
        for (Code c: codeList){
            dbCodeManager = new DBCodeManager(ScanActivity.this);
            dbCodeManager.addCodeToDB(c.getCode(), c.getTime(), c.getType(), c.getGps(), c.getIsSent(), orderNum);
        }
    }


    private void saveNewCodeToLocalMemory(String decodedData, String decodedLabelType){
        Log.i(TAG, "saveNewCodeToLocalMemory");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        Code code = new Code(decodedData, decodedLabelType, Arrays.asList(currentLocation.getLongitude(), currentLocation.getLatitude()));
        if (Profile.getIsDoFilter() == 1){
            Log.d(TAG, "Do filter");
        } else {
            codeList.add(code);
            Log.d(TAG, "Code was saved: " + code);
            updateRecyclerView();
        }


        // add new code to a Recycler View
    }

    private void updateRecyclerView() {
        Log.i(TAG, "updateRecyclerView");
        codeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);


    }

    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "BroadcastReceiver");
            String action = intent.getAction();
            assert action != null;
            if (action.equals(getResources().getString(R.string.activity_intent_filter_action))) {
                try {handleScanResult(intent);
                } catch (Exception e) {e.printStackTrace();}
            }
        }
    };

    private void handleScanResult(Intent initiatingIntent) {
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
        if (decodedSource == null) {
            Log.d(TAG, "handleScanResult: decodedSource == null");
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
            decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type_legacy));
        }
        saveNewCodeToLocalMemory(decodedData, decodedLabelType);

    }

    private void getLastLocation() {
        Log.i(TAG, "getLastLocation");
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int FINE_PERMISSION_CODE = 1;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;}
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {currentLocation = location;}});
    }
}