package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.alex.scancode.R;
import com.alex.scancode.managers.databases.DBFilterManager;
import com.alex.scancode.models.settings.Filter;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private EditText settings_filter_code_length, settings_filter_prefix, settings_filter_suffix,
            settings_filter_ending;
    private EditText settings_filter_labelType; // change to multiple choice
    private Switch settings_filter_nonUniqueCodeAllow,settings_filter_doFilter;
    private Button settings_btn_do_to_default;
    private DBFilterManager dbFilterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dbFilterManager = new DBFilterManager(SettingsActivity.this);
        dbFilterManager.onCreate(dbFilterManager.getWritableDatabase());

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


        Filter filter = dbFilterManager.getFilterData();

        System.out.println(filter.toString());
        settings_filter_code_length.setText(String.valueOf(filter.getCodeLength()));
        settings_filter_prefix.setText(String.valueOf(filter.getPrefix()));
        settings_filter_suffix.setText(String.valueOf(filter.getSuffix()));
        settings_filter_ending.setText(String.valueOf(filter.getEnding()));
        settings_filter_labelType.setText(String.valueOf(filter.getType()));

        settings_filter_nonUniqueCodeAllow.setChecked(filter.getIsNonUniqueCodeAllow() == 1);
        settings_filter_doFilter.setChecked(Filter.getIsDoFilter() == 1);

//        settings_btn_do_to_default.setOnClickListener(view -> {
//            Log.d(TAG, "initializeFilterSection: come back to default settings");
//            dbFilterManager.initializeDBTableFilter();
//        });

    }
}