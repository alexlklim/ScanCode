package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alex.scancode.R;

public class SpecialOrderActivity extends AppCompatActivity {
    private static final String TAG = "SpecialOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_order);
        tryToOpenSpecialOrder();

        initializeRecyclerView();


    }

    private void initializeRecyclerView() {
        Log.i(TAG, "initializeRecyclerView: ");
    }


    private void tryToOpenSpecialOrder() {
        Log.i(TAG, "tryToOpenSpecialOrder: ");
        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", -1);
        if (orderId == -1){
            Toast.makeText(this, "Something wring", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}