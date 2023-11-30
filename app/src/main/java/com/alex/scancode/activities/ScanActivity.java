package com.alex.scancode.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.R;
import com.alex.scancode.managers.GPSManager;
import com.alex.scancode.managers.adapters.CodeAdapter;
import com.alex.scancode.models.Code;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ScanActivity extends AppCompatActivity {
    private static final String TAG = "ScanActivity";
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private RecyclerView recyclerView;
    private CodeAdapter codeAdapter;
    private Button scan_btn_do_finish_order;


    private List<Code> codeList = new LinkedList<>();
    private String orderNumber;



    private TextView scan_text_stopwatch, scan_text_date_time, scan_text_order_number;
    private CountDownTimer countDownTimer;
    private long startTimeInMillis = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        scan_text_stopwatch = findViewById(R.id.scan_text_stopwatch);
        scan_text_date_time = findViewById(R.id.scan_text_date_time);
        scan_text_order_number = findViewById(R.id.scan_text_order_number);

        Intent intent = getIntent();
        orderNumber = intent.getStringExtra("ORDER_NUMBER");
        scan_text_order_number.setText("Order N. " + orderNumber);


        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);

        startStopwatch();
        displayDateTime();

        // initialize recyclerView to show data
        recyclerView = findViewById(R.id.recyclerView);
        codeAdapter = new CodeAdapter(codeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(codeAdapter);


        scan_btn_do_finish_order = findViewById(R.id.scan_btn_do_finish_order);
        scan_btn_do_finish_order.setOnClickListener(v ->{
            Log.d(TAG, "onClick: scan_btn_do_finish_order was pressed");
            saveNewOrder();
        });
    }

    private void saveNewOrder() {
        Log.i(TAG, "saveNewOrder: ");
    }

    private void saveNewCodesToDB(int orderNum) {
        Log.d(TAG, "saveNewCodesToDB with orderNumber=" + orderNum);

    }


    private void saveNewCodeToLocalMemory(String decodedData, String decodedLabelType){
        Log.i(TAG, "saveNewCodeToLocalMemory: " + decodedData);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        Code code = new Code(decodedData, decodedLabelType, GPSManager.convertGpsToString(currentLocation));
        codeList.add(code);
        Log.d(TAG, "Code was saved: " + code);
        updateRecyclerView();


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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int FINE_PERMISSION_CODE = 1;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;}
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {if (location != null) {currentLocation = location;}});
    }







    private void startStopwatch() {
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                startTimeInMillis += 1000;
                updateTimer();}
            @Override
            public void onFinish() {}};
        countDownTimer.start();
    }

    private void stopStopwatch() {if (countDownTimer != null) {countDownTimer.cancel();}}
    private void updateTimer() {
        int seconds = (int) (startTimeInMillis / 1000), minutes = seconds / 60, hours = minutes / 60;
        @SuppressLint("DefaultLocale") String timeFormatted = String.format("%02d:%02d:%02d", hours % 24, minutes % 60, seconds % 60);
        scan_text_stopwatch.setText(timeFormatted);
    }
    private void displayDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault());
        String formattedDateTime = dateFormat.format(new Date());
        scan_text_date_time.setText(formattedDateTime);
    }
}