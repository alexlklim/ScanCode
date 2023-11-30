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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.MainActivity;
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
    private List<Code> codeList = new LinkedList<>();

    private String orderNumber;


    private Button sc_btn_doFinishOrder;

    private CountDownTimer countDownTimer;
    private long startTimeInMillis = 0;



    private TextView sc_tv_timer, sc_tv_dateTime, sc_tv_orderNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        initializeTopBar();
        
        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);
        
        // initialize recyclerView to show data
        recyclerView = findViewById(R.id.sc_rv_codes);
        codeAdapter = new CodeAdapter(codeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(codeAdapter);

        addListenerForFinish();
    }

    private void addListenerForFinish() {
        Log.i(TAG, "addListenerForFinish: ");
        sc_btn_doFinishOrder = findViewById(R.id.sc_btn_doFinishOrder);
        sc_btn_doFinishOrder.setOnClickListener(v -> {
            Log.d(TAG, "onClick: sc_btn_doFinishOrder was pressed");
            showDialogConfirmationFinishOrder();
        });
        
        
    }

    private void initializeTopBar() {
        Log.i(TAG, "initializeTopBar: ");
        sc_tv_timer = findViewById(R.id.sc_tv_timer);
        sc_tv_dateTime = findViewById(R.id.sc_tv_dateTime);
        sc_tv_orderNumber = findViewById(R.id.sc_tv_orderNumber);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault());
        String formattedDateTime = dateFormat.format(new Date());
        sc_tv_dateTime.setText(formattedDateTime);

        startStopwatch();

        Intent intent = getIntent();
        orderNumber = intent.getStringExtra("ORDER_NUMBER");
        sc_tv_orderNumber.setText("Order N. " + orderNumber);
    }


    private void saveNewCodeToLocalMemory(String decodedData, String decodedLabelType){
        Log.i(TAG, "saveNewCodeToLocalMemory: " + decodedData);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        Code code = new Code(decodedData, decodedLabelType, GPSManager.convertGpsToString(currentLocation));
        codeList.add(code);
        Log.d(TAG, "Code was saved: " + code);
        updateRecyclerView();
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
        Log.i(TAG, "startStopwatch: ");
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                startTimeInMillis += 1000;
                updateTimer();}
            @Override
            public void onFinish() {}};
        countDownTimer.start();
    }

    private void stopStopwatch() {
        Log.i(TAG, "stopStopwatch: ");
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    private void updateTimer() {
        int seconds = (int) (startTimeInMillis / 1000), minutes = seconds / 60, hours = minutes / 60;
        String timeFormatted = String.format("%02d:%02d:%02d", hours % 24, minutes % 60, seconds % 60);
        sc_tv_timer.setText(timeFormatted);
    }

    AlertDialog alertDialog;

    private void showDialogConfirmationFinishOrder() {
        Log.i(TAG, "showDialogConfirmationFinishOrder: ");
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_confirmation, null);

        TextView d_tv_confirmation = dialog.findViewById(R.id.d_tv_confirmation);
        Button d_btn_yes = dialog.findViewById(R.id.d_btn_yes);
        Button d_btn_no = dialog.findViewById(R.id.d_btn_no);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        d_tv_confirmation.setText(new StringBuilder(getString(R.string.d_confirm_finishOrder)));

        d_btn_yes.setOnClickListener(v -> {
            Log.d(TAG, "showDialogConfirmationFinishOrder: d_btn_yes was pressed");
            stopStopwatch();
            boolean result = saveNewOrder();
            if (result)
                Toast.makeText(ScanActivity.this, getString(R.string.toast_order_has_been_saved), Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
            showDialogOrderSavedResult(result);
        });

        d_btn_no.setOnClickListener(v -> {
            Log.d(TAG, "showDialogConfirmationFinishOrder: d_btn_no was pressed");
            alertDialog.dismiss();
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDialogOrderSavedResult(boolean result) {
        Log.i(TAG, "showDialogOrderSavedResult: ");
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_order_saved_result, null);

        TextView d_tv_confirmation = dialog.findViewById(R.id.d_tv_confirmation);
        ImageView d_iv_result = dialog.findViewById(R.id.d_iv_result);
        Button d_btn_comeBackToMenu = dialog.findViewById(R.id.d_btn_comeBackToMenu);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);

        if (result){
            d_tv_confirmation.setText(new StringBuilder(getString(R.string.d_result_success)));
            d_iv_result.setImageResource(R.drawable.ic_success);
        } else {
            d_tv_confirmation.setText(new StringBuilder(getString(R.string.d_result_fail)));
            d_iv_result.setImageResource(R.drawable.ic_fail);
        }
        d_btn_comeBackToMenu.setOnClickListener(v -> {
            Log.d(TAG, "showDialogOrderSavedResult: d_btn_comeBackToMenu was pressed");
            alertDialog.dismiss();
//            Intent intent = new Intent(ScanActivity.this, MainActivity.class);
            finish();
//            startActivity(intent);
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean saveNewOrder() {
        Log.i(TAG, "saveNewOrder: ");
        return true;
    }


}