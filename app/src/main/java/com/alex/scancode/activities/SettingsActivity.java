package com.alex.scancode.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.alex.scancode.R;
import com.alex.scancode.managers.SettingsManager;
import com.alex.scancode.models.enums.LabelType;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    SwitchCompat s_filter_doFilter, s_filter_isNonUniqueAllow, s_filter_checkCodeLength, s_filter_advancedFilter;
    EditText s_filter_codeLength, s_filter_codeLengthMIN, s_filter_codeLengthMAX, s_filter_prefix, s_filter_suffix, s_filter_ending;
    Spinner s_filter_labelType;
    Button s_btn_toDefault;
    LinearLayout s_f_section_doFilter, s_f_section_checkCodeLength, s_f_section_advancedFilter;
    private SettingsManager settingsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsManager = new SettingsManager(this);

        initializeFilterSection();
    }


    private void initializeFilterSection(){
        Log.i(TAG, "initializeFilterSection: ");
        s_f_section_doFilter = findViewById(R.id.s_f_section_doFilter);
        s_f_section_checkCodeLength = findViewById(R.id.s_f_section_checkCodeLength);
        s_f_section_advancedFilter = findViewById(R.id.s_f_section_advancedFilter);

        s_filter_doFilter = findViewById(R.id.s_filter_doFilter);
        s_filter_checkCodeLength = findViewById(R.id.s_filter_checkCodeLength);
        s_filter_advancedFilter = findViewById(R.id.s_filter_advancedFilter);

        s_filter_isNonUniqueAllow = findViewById(R.id.s_filter_isNonUniqueAllow);

        s_filter_codeLength = findViewById(R.id.s_filter_codeLength);
        s_filter_codeLengthMIN = findViewById(R.id.s_filter_codeLengthMIN);
        s_filter_codeLengthMAX = findViewById(R.id.s_filter_codeLengthMAX);

        s_filter_prefix = findViewById(R.id.s_filter_prefix);
        s_filter_suffix = findViewById(R.id.s_filter_suffix);
        s_filter_ending = findViewById(R.id.s_filter_ending);


        s_filter_labelType = findViewById(R.id.s_filter_labelType);
        List<String> typeList = LabelType.getLabelTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_filter_labelType.setAdapter(adapter);



        s_filter_doFilter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked)
                hideSectionDoFilter();
            else
                showSectionDoFilter();
        });

        s_filter_checkCodeLength.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked)
                hideSectionCheckCodeLength();
            else
                showSectionCheckCodeLength();
        });

        s_filter_advancedFilter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked)
                hideSectionAdvancedFilter();
            else
                showSectionAdvancedFilter();
        });


        if (!s_filter_doFilter.isChecked()) {
            hideSectionDoFilter();
        }
        if (!s_filter_checkCodeLength.isChecked()) {
            hideSectionCheckCodeLength();
        }
        if (!s_filter_advancedFilter.isChecked()) {
            hideSectionAdvancedFilter();
        }




    }

    private void hideSectionDoFilter() {s_f_section_doFilter.setVisibility(View.GONE);}
    private void showSectionDoFilter() {s_f_section_doFilter.setVisibility(View.VISIBLE);}

    private void hideSectionCheckCodeLength() {s_f_section_checkCodeLength.setVisibility(View.GONE);}
    private void showSectionCheckCodeLength() {s_f_section_checkCodeLength.setVisibility(View.VISIBLE);}

    private void hideSectionAdvancedFilter() {s_f_section_advancedFilter.setVisibility(View.GONE);}
    private void showSectionAdvancedFilter() {s_f_section_advancedFilter.setVisibility(View.VISIBLE);}

}