package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alex.scancode.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}