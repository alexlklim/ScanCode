package com.alex.scancode.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.alex.scancode.R;
import com.alex.scancode.managers.SettingsManager;
import com.alex.scancode.models.enums.LabelType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    SwitchCompat s_filter_isNonUniqueAllow, s_filter_checkCodeLength, s_filter_advancedFilter, s_filter_isServerConfigured;
    EditText s_filter_codeLength, s_filter_codeLengthMIN, s_filter_codeLengthMAX, s_filter_prefix, s_filter_suffix, s_filter_ending, s_filter_serverAddress;
    Spinner s_filter_labelType;
    Button s_btn_toDefault, s_btn_comeBack, s_btn_saveSettings;
    ImageButton s_btn_lang_EN, s_btn_lang_PL, s_btn_lang_UA;
    LinearLayout s_f_section_doFilter, s_f_section_checkCodeLength, s_f_section_advancedFilter, s_f_section_serverConfiguration;
    private SettingsManager settingsManager;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsManager = new SettingsManager(this);
        initializeFilterSection();
        initializeServerConfigurationSection();
        getSharedPreferences();
        createListenerForButtons();
        addListenerForLangButtons();
    }

    private void addListenerForLangButtons() {
        s_btn_lang_EN = findViewById(R.id.s_btn_lang_ENG);
        s_btn_lang_PL = findViewById(R.id.s_btn_lang_PL);
        s_btn_lang_UA = findViewById(R.id.s_btn_lang_UA);
        s_btn_lang_EN.setOnClickListener(v -> {
            changeLanguage("EN");
        });
        s_btn_lang_PL.setOnClickListener(v -> {
            changeLanguage("PL");
        });
        s_btn_lang_UA.setOnClickListener(v -> {
            changeLanguage("UK");
        });

    }
    private void changeLanguage(String languageCode) {
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.locale = new Locale(languageCode);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }



    private void createListenerForButtons() {
        s_btn_toDefault = findViewById(R.id.s_btn_toDefault);
        s_btn_comeBack = findViewById(R.id.s_btn_comeBack);
        s_btn_saveSettings = findViewById(R.id.s_btn_saveSettings);

        s_btn_toDefault.setOnClickListener(v -> {
            Log.d(TAG, "createListenerForButtons: s_btn_toDefault was pressed");
            showDialogConfirmationToDefault();
        });

        s_btn_comeBack.setOnClickListener(v -> {
            Log.d(TAG, "createListenerForButtons: s_btn_comeBack was pressed");
            finish();
        });
        s_btn_saveSettings.setOnClickListener(v -> {
            Log.d(TAG, "createListenerForButtons: s_btn_saveSettings was pressed");
            showDialogConfirmationSaveSettings();
        });
    }


    private void showDialogConfirmationToDefault() {
        Log.d(TAG, "showDialogConfirmationToDefault: ");
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_confirmation, null);
        TextView d_tv_confirmation = dialog.findViewById(R.id.d_tv_confirmation);
        Button d_btn_yes = dialog.findViewById(R.id.d_btn_yes);
        Button d_btn_no = dialog.findViewById(R.id.d_btn_no);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        d_tv_confirmation.setText(new StringBuilder(getString(R.string.d_confirm_toDefault)));

        d_btn_yes.setOnClickListener(v -> {
            Log.d(TAG, "showDialogConfirmationToDefault: d_btn_yes");
            comeBackToDefaultSettings();
            Toast.makeText(SettingsActivity.this, getString(R.string.toast_settings_reset), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
            alertDialog.dismiss();
            finish();
            startActivity(intent);
        });

        d_btn_no.setOnClickListener(v -> {
            Log.d(TAG, "showDialogConfirmationToDefault: d_btn_no");
            alertDialog.dismiss();
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDialogConfirmationSaveSettings() {
        Log.d(TAG, "showDialogConfirmationSaveSettings: ");
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_confirmation, null);
        TextView d_tv_confirmation = dialog.findViewById(R.id.d_tv_confirmation);
        Button d_btn_yes = dialog.findViewById(R.id.d_btn_yes);
        Button d_btn_no = dialog.findViewById(R.id.d_btn_no);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        d_tv_confirmation.setText(new StringBuilder(getString(R.string.d_confirm_saveSettings)));

        d_btn_yes.setOnClickListener(v -> {
            Log.d(TAG, "showDialogConfirmationSaveSettings: d_btn_yes");
            saveNewSettings();
            Toast.makeText(SettingsActivity.this, getString(R.string.toast_settings_saved), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
            alertDialog.dismiss();
            finish();
            startActivity(intent);
        });

        d_btn_no.setOnClickListener(v -> {
            Log.d(TAG, "showDialogConfirmationSaveSettings: d_btn_no");
            alertDialog.dismiss();
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveNewSettings() {
        Log.d(TAG, "saveNewSettings: ");
        System.out.println(s_filter_codeLengthMAX.getText());
        settingsManager.setFilterSection(
                s_filter_isNonUniqueAllow.isChecked(),

                s_filter_checkCodeLength.isChecked(),
                parseIntOrDefault(s_filter_codeLength.getText().toString()),
                parseIntOrDefault(s_filter_codeLengthMIN.getText().toString()),
                parseIntOrDefault(s_filter_codeLengthMAX.getText().toString()),

                s_filter_advancedFilter.isChecked(),
                s_filter_prefix.getText().toString(), s_filter_suffix.getText().toString(), s_filter_ending.getText().toString(),
                s_filter_labelType.getSelectedItem().toString(),

                s_filter_isServerConfigured.isChecked(), s_filter_serverAddress.getText().toString()
        );
    }
    private int parseIntOrDefault(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void comeBackToDefaultSettings() {
        settingsManager.comeBackToDefaultSettings();
    }


    //initialization activities
    private void initializeServerConfigurationSection() {
        Log.i(TAG, "initializeServerConfigurationSection: ");
        s_f_section_serverConfiguration = findViewById(R.id.s_f_section_serverConfiguration);
        s_filter_isServerConfigured = findViewById(R.id.s_filter_isServerConfigured);
        s_filter_serverAddress = findViewById(R.id.s_filter_serverAddress);

        s_filter_isServerConfigured.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked)
                hideSectionServerConfiguration();
            else
                showSectionServerConfiguration();
        });

        if (!s_filter_isServerConfigured.isChecked()) {
            hideSectionServerConfiguration();
        }

    }

    private void initializeFilterSection() {
        Log.i(TAG, "initializeFilterSection: ");
        s_f_section_doFilter = findViewById(R.id.s_f_section_doFilter);
        s_f_section_checkCodeLength = findViewById(R.id.s_f_section_checkCodeLength);
        s_f_section_advancedFilter = findViewById(R.id.s_f_section_advancedFilter);

        s_filter_checkCodeLength = findViewById(R.id.s_filter_checkCodeLength);
        s_filter_advancedFilter = findViewById(R.id.s_filter_advancedFilter);
        s_filter_isNonUniqueAllow = findViewById(R.id.s_filter_isNonUniqueAllow);

        s_filter_codeLength = findViewById(R.id.s_filter_codeLength);
        s_filter_codeLengthMIN = findViewById(R.id.s_filter_codeLengthMIN);
        s_filter_codeLengthMAX = findViewById(R.id.s_filter_codeLengthMAX);

        s_filter_prefix = findViewById(R.id.s_filter_prefix);
        s_filter_suffix = findViewById(R.id.s_filter_suffix);
        s_filter_ending = findViewById(R.id.s_filter_ending);

        // initialize spinner
        s_filter_labelType = findViewById(R.id.s_filter_labelType);
        List<String> labelTypes = LabelType.getListLabelTypes();
        int index = labelTypes.indexOf(settingsManager.getCodeLabelType());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new ArrayList<>(labelTypes));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_filter_labelType.setAdapter(adapter);
        if (index != -1) {
            s_filter_labelType.setSelection(index);
        }



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


        if (!s_filter_checkCodeLength.isChecked()) {
            hideSectionCheckCodeLength();
        }
        if (!s_filter_advancedFilter.isChecked()) {
            hideSectionAdvancedFilter();
        }


    }

    private void getSharedPreferences() {
        Log.i(TAG, "getSharedPreferences: ");
        s_filter_checkCodeLength.setChecked(settingsManager.isCheckCodeLength());
        s_filter_advancedFilter.setChecked(settingsManager.isDoAdvancedFilter());
        s_filter_isServerConfigured.setChecked(settingsManager.isServerConfigured());
        s_filter_isNonUniqueAllow.setChecked(settingsManager.isNonUniqueCodeAllow());

        s_filter_codeLength.setText(String.valueOf(settingsManager.getCodeLength()));
        s_filter_codeLengthMIN.setText(String.valueOf(settingsManager.getCodeLengthMIN()));
        s_filter_codeLengthMAX.setText(String.valueOf(settingsManager.getCodeLengthMAX()));

        s_filter_prefix.setText(settingsManager.getCodePrefix());
        s_filter_suffix.setText(settingsManager.getCodeSuffix());
        s_filter_ending.setText(settingsManager.getCodeEnding());

        s_filter_serverAddress.setText(settingsManager.getServerAddress());
    }


    // hide and show some section
    private void hideSectionDoFilter() {
        s_f_section_doFilter.setVisibility(View.GONE);
    }

    private void showSectionDoFilter() {
        s_f_section_doFilter.setVisibility(View.VISIBLE);
    }

    private void hideSectionCheckCodeLength() {
        s_f_section_checkCodeLength.setVisibility(View.GONE);
    }

    private void showSectionCheckCodeLength() {
        s_f_section_checkCodeLength.setVisibility(View.VISIBLE);
    }

    private void hideSectionAdvancedFilter() {
        s_f_section_advancedFilter.setVisibility(View.GONE);
    }

    private void showSectionAdvancedFilter() {
        s_f_section_advancedFilter.setVisibility(View.VISIBLE);
    }

    private void hideSectionServerConfiguration() {
        s_f_section_serverConfiguration.setVisibility(View.GONE);
    }

    private void showSectionServerConfiguration() {
        s_f_section_serverConfiguration.setVisibility(View.VISIBLE);
    }

}