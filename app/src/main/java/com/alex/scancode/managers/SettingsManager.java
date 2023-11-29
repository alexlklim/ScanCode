package com.alex.scancode.managers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;

import com.alex.scancode.models.enums.Lang;

public class SettingsManager extends AppCompatActivity {
    private SharedPreferences preferences;
    public static final String PREF_NAME = "ScanApp";
    public static final String KEY_ID = "identifier", KEY_LOGIN = "login", KEY_PW = "pw", KEY_LANG = "lang",
            KEY_IS_DO_FILTER = "isDoFilter", KEY_IS_NON_UNIQUE_CODE_ALLOW = "isNonUniqueCodeAllow",

            KEY_IS_CHECK_CODE_LENGTH = "isCheckCodeLength",
            KEY_CODE_LENGTH = "codeLength", KEY_CODE_LENGTH_MAX = "codeLengthMAX", KEY_CODE_LENGTH_MIN = "codeLengthMIN",

            KEY_ADVANCED_FILTER = "advancedFilter",
            KEY_PREFIX = "prefix", KEY_SUFFIX = "suffix", KEY_ENDING = "ending", KEY_LABEL_TYPE = "labelType",

            KEY_IS_SERVER_CONFIGURED = "isServerConfigured", KEY_SERVER_ADDRESS = "serverAddress";


    public SettingsManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public String getLogin(){
        return preferences.getString(KEY_LOGIN, "admin");
    }
    public String getPassword(){
        return preferences.getString(KEY_PW, "admin");
    }




    public boolean isNonUniqueCodeAllow() {return preferences.getBoolean(KEY_IS_NON_UNIQUE_CODE_ALLOW, false);}
    public void setIsNonUniqueCodeAllow(boolean bool) {preferences.edit().putBoolean(KEY_IS_NON_UNIQUE_CODE_ALLOW, bool).apply();}













}
