package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.alex.scancode.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private EditText settings_filter_code_length, settings_filter_prefix, settings_filter_suffix,
            settings_filter_ending;
    private EditText settings_filter_labelType; // change to multiple choice
    private Switch settings_filter_nonUniqueCodeAllow,settings_filter_doFilter;
    private Button settings_btn_do_to_default;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeFilterSection();
    }


    private void initializeFilterSection(){
        Log.i(TAG, "initializeFilterSection: ");
        settings_filter_code_length = findViewById(R.id.settings_filter_code_length);
        settings_filter_prefix = findViewById(R.id.settings_filter_prefix);
        settings_filter_suffix = findViewById(R.id.settings_filter_suffix);
        settings_filter_ending = findViewById(R.id.settings_filter_ending);
        settings_filter_labelType = findViewById(R.id.settings_filter_labelType);
        settings_filter_nonUniqueCodeAllow = findViewById(R.id.settings_filter_nonUniqueCodeAllow);
        settings_filter_doFilter = findViewById(R.id.settings_filter_doFilter);
        settings_btn_do_to_default = findViewById(R.id.settings_btn_do_to_default);

        // get values from DB and set values in View



    }
}