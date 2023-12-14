package com.alex.scancode.activities;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.alex.scancode.MainActivity;
import com.alex.scancode.R;
import com.alex.scancode.managers.Ans;
import com.alex.scancode.managers.LocaleHelper;
import com.alex.scancode.managers.SettingsManager;
import com.alex.scancode.models.enums.FileType;
import com.alex.scancode.models.enums.LabelType;
import com.alex.scancode.models.enums.Lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    SwitchCompat s_filter_isNonUniqueAllow, s_filter_checkCodeLength, s_filter_advancedFilter,
            s_filter_isServerConfigured, s_filter_autoSynch,
            s_filter_isAllowEditingDuringScan, s_filter_isAllowEditingOrders, s_filter_isAddLocationToCode;
    EditText s_filter_codeLength, s_filter_codeLengthMIN, s_filter_codeLengthMAX, s_filter_prefix,
            s_filter_suffix, s_filter_ending, s_filter_serverAddress, s_filter_identifier,
            s_filter_oldPw, s_filter_newPw, s_filter_login;
    Spinner s_filter_labelType, s_filter_lang, s_filter_fileType;
    Button s_btn_toDefault, s_btn_comeBack, s_btn_saveSettings;
    LinearLayout s_f_section_doFilter, s_f_section_checkCodeLength, s_f_section_advancedFilter, s_f_section_serverConfiguration;
    private SettingsManager settingsManager;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsManager = new SettingsManager(this);
        initializeProfileSection();
        initializeFilterSection();
        initializeServerConfigurationSection();
        initializeAdminConfigSection();
        getSharedPreferences();
        createListenerForButtons();
    }


    private void createListenerForButtons() {
        s_btn_toDefault = findViewById(R.id.s_btn_toDefault);
        s_btn_comeBack = findViewById(R.id.s_btn_comeBack);
        s_btn_saveSettings = findViewById(R.id.s_btn_saveSettings);

        s_btn_toDefault.setOnClickListener(v -> showDialogConfirmationToDefault());
        s_btn_comeBack.setOnClickListener(v -> finish());
        s_btn_saveSettings.setOnClickListener(v -> showDialogConfirmationSaveSettings());
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
            Ans.showToast(getString(R.string.toast_settings_reset), this);


            Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
            alertDialog.dismiss();
            finish();
            startActivity(intent);
        });
        d_btn_no.setOnClickListener(v -> alertDialog.dismiss());
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

        d_btn_no.setOnClickListener(v -> alertDialog.dismiss());
        d_btn_yes.setOnClickListener(v -> {
            if (true){
                alertDialog.dismiss();
                saveNewSettings();
                Ans.showToast(getString(R.string.toast_settings_saved), this);

            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveNewSettings() {
        Log.d(TAG, "saveNewSettings: ");
        settingsManager.setProfileSection(s_filter_login.getText().toString(), s_filter_newPw.getText().toString());

        changeLang(s_filter_lang.getSelectedItem().toString());

        settingsManager.setFilterConfig(s_filter_isNonUniqueAllow.isChecked(),
                s_filter_checkCodeLength.isChecked(), parseIntOrDefault(s_filter_codeLength.getText().toString()), parseIntOrDefault(s_filter_codeLengthMIN.getText().toString()), parseIntOrDefault(s_filter_codeLengthMAX.getText().toString()),
                s_filter_advancedFilter.isChecked(), s_filter_prefix.getText().toString(), s_filter_suffix.getText().toString(), s_filter_ending.getText().toString(),
                s_filter_labelType.getSelectedItem().toString());

        settingsManager.setServerConfig(s_filter_isServerConfigured.isChecked(), s_filter_autoSynch.isChecked(),
                s_filter_serverAddress.getText().toString(), parseIntOrDefault(s_filter_identifier.getText().toString()));

        settingsManager.setAdminConfig(s_filter_isAllowEditingDuringScan.isChecked(),
                s_filter_isAllowEditingOrders.isChecked(),
                s_filter_isAddLocationToCode.isChecked());
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
        s_filter_autoSynch = findViewById(R.id.s_filter_autoSynch);

        s_filter_serverAddress = findViewById(R.id.s_filter_serverAddress);
        s_filter_identifier = findViewById(R.id.s_filter_identifier);


        s_filter_isServerConfigured.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) s_f_section_serverConfiguration.setVisibility(View.GONE);
            else s_f_section_serverConfiguration.setVisibility(View.VISIBLE);
        });

        if (!s_filter_isServerConfigured.isChecked()) s_f_section_serverConfiguration.setVisibility(View.GONE);
    }

    private void initializeAdminConfigSection() {
        s_filter_isAllowEditingDuringScan = findViewById(R.id.s_filter_isAllowEditingDuringScan);
        s_filter_isAllowEditingOrders = findViewById(R.id.s_filter_isAllowEditingOrders);
        s_filter_isAddLocationToCode = findViewById(R.id.s_filter_isAddLocationToCode);
    }

    private void initializeProfileSection() {
        s_filter_oldPw = findViewById(R.id.s_filter_oldPw);
        s_filter_newPw = findViewById(R.id.s_filter_newPw);
        s_filter_login = findViewById(R.id.s_filter_login);
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


       initializeSpinners();

        s_filter_checkCodeLength.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) s_f_section_checkCodeLength.setVisibility(View.GONE);
            else s_f_section_checkCodeLength.setVisibility(View.VISIBLE);
        });
        s_filter_advancedFilter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) s_f_section_advancedFilter.setVisibility(View.GONE);
            else s_f_section_advancedFilter.setVisibility(View.VISIBLE);
        });

        if (!s_filter_checkCodeLength.isChecked()) s_f_section_checkCodeLength.setVisibility(View.GONE);
        if (!s_filter_advancedFilter.isChecked()) s_f_section_advancedFilter.setVisibility(View.GONE);
    }

    private void initializeSpinners() {
        // initialize spinner labelType
        s_filter_labelType = findViewById(R.id.s_filter_labelType);
        List<String> labelTypes = LabelType.getListLabelTypes();
        int indexLabelType = labelTypes.indexOf(settingsManager.getCodeLabelType());
        ArrayAdapter<String> adapterLabelType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(labelTypes));
        adapterLabelType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_filter_labelType.setAdapter(adapterLabelType);
        if (indexLabelType != -1) s_filter_labelType.setSelection(indexLabelType);

        // initialize spinner Lang
        s_filter_lang = findViewById(R.id.s_filter_lang);
        List<String> languages = Lang.getAllLang();
        int indexLang = languages.indexOf(settingsManager.getLang());
        ArrayAdapter<String> adapterLang = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(languages));
        adapterLabelType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_filter_lang.setAdapter(adapterLang);
        if (indexLang != -1) s_filter_lang.setSelection(indexLang);

        //  initialize spinner FileType
        s_filter_fileType = findViewById(R.id.s_filter_fileType);
        List<String> fileTypes = FileType.getFileTypeList();
        int indexFileTypes = fileTypes.indexOf(settingsManager.getCodeLabelType());
        ArrayAdapter<String> adapterFileType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new ArrayList<>(fileTypes));
        adapterFileType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_filter_fileType.setAdapter(adapterFileType);
        if (indexFileTypes != -1) s_filter_fileType.setSelection(indexFileTypes);

    }

    private void getSharedPreferences() {
        Log.i(TAG, "getSharedPreferences: ");
        s_filter_isNonUniqueAllow.setChecked(settingsManager.isNonUniqueCodeAllow());

        s_filter_checkCodeLength.setChecked(settingsManager.isCheckCodeLength());
        s_filter_codeLength.setText(String.valueOf(settingsManager.getCodeLength()));
        s_filter_codeLengthMIN.setText(String.valueOf(settingsManager.getCodeLengthMIN()));
        s_filter_codeLengthMAX.setText(String.valueOf(settingsManager.getCodeLengthMAX()));

        s_filter_advancedFilter.setChecked(settingsManager.isDoAdvancedFilter());
        s_filter_prefix.setText(settingsManager.getCodePrefix());
        s_filter_suffix.setText(settingsManager.getCodeSuffix());
        s_filter_ending.setText(settingsManager.getCodeEnding());

        s_filter_isServerConfigured.setChecked(settingsManager.isServerConfigured());
        s_filter_autoSynch.setChecked(settingsManager.isAutoSynch());
        s_filter_serverAddress.setText(settingsManager.getServerAddress());
        s_filter_identifier.setText(String.valueOf(settingsManager.getIdentifier()));

        s_filter_isAllowEditingDuringScan.setChecked(settingsManager.isAllowEditingDuringScan());
        s_filter_isAllowEditingOrders.setChecked(settingsManager.isAllowEditingOrders());
        s_filter_isAddLocationToCode.setChecked(settingsManager.isAddLocationToCode());


    }


    public void changeLang(String lang){
        String language = Lang.getCodeByName(lang);
        LocaleHelper.setLocale(SettingsActivity.this, language);
        recreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }




}