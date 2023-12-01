package com.alex.scancode.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.scancode.models.enums.LabelType;

public class SettingsManager extends AppCompatActivity {
    private static final String TAG = "SettingsManager";

    private SharedPreferences preferences;
    public static final String PREF_NAME = "ScanApp";
    public static final String KEY_ID = "identifier", KEY_LOGIN = "login", KEY_PW = "pw", KEY_LANG = "lang",
            KEY_IS_NON_UNIQUE_CODE_ALLOW = "isNonUniqueCodeAllow",

    KEY_IS_CHECK_CODE_LENGTH = "isCheckCodeLength",
            KEY_CODE_LENGTH = "codeLength", KEY_CODE_LENGTH_MAX = "codeLengthMAX", KEY_CODE_LENGTH_MIN = "codeLengthMIN",

    KEY_ADVANCED_FILTER = "advancedFilter",
            KEY_PREFIX = "prefix", KEY_SUFFIX = "suffix", KEY_ENDING = "ending", KEY_LABEL_TYPE = "labelType",

    KEY_IS_SERVER_CONFIGURED = "isServerConfigured", KEY_SERVER_ADDRESS = "serverAddress";


    public SettingsManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public void setProfileSection(int identifier, String login, String pw) {
        preferences.edit().putInt(KEY_ID, identifier).apply();
        preferences.edit().putString(KEY_LOGIN, login).apply();
        preferences.edit().putString(KEY_PW, pw).apply();
    }

    public int getIdentifier() {
        return preferences.getInt(KEY_ID, 100000);
    }

    public String getLogin() {
        return preferences.getString(KEY_LOGIN, "admin");
    }

    public String getPassword() {
        return preferences.getString(KEY_PW, "admin");
    }


    public String getLang() {
        return preferences.getString(KEY_LANG, "EN");
    }

    public void setLang(String lang) {
        preferences.edit().putString(KEY_LANG, lang).apply();
    }


    public void setFilterSection(boolean isNonUniqueCodeAllow,
                                 boolean isCheckCodeLength, int length, int lengthMIN, int lengthMAX,
                                 boolean isDoAdvancedFilter, String prefix, String suffix, String ending, String labelType,
                                 boolean isServerConfigured, String serverAddress) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(KEY_IS_NON_UNIQUE_CODE_ALLOW, isNonUniqueCodeAllow)
                .putBoolean(KEY_IS_CHECK_CODE_LENGTH, isCheckCodeLength)
                .putInt(KEY_CODE_LENGTH, length).putInt(KEY_CODE_LENGTH_MIN, lengthMIN).putInt(KEY_CODE_LENGTH_MAX, lengthMAX)

                .putBoolean(KEY_ADVANCED_FILTER, isDoAdvancedFilter)
                .putString(KEY_PREFIX, prefix).putString(KEY_SUFFIX, suffix).putString(KEY_ENDING, ending)
//                .putStringSet(KEY_LABEL_TYPE, labelTypes)
                .putString(KEY_LABEL_TYPE, labelType)

                .putBoolean(KEY_IS_SERVER_CONFIGURED, isServerConfigured)
                .putString(KEY_SERVER_ADDRESS, serverAddress)

                .apply();
    }

    public void comeBackToDefaultSettings() {
        Log.i(TAG, "comeBackToDefaultSettings: ");
        setFilterSection(false,
                false, 0, 0, 0,
                false, "", "","", LabelType.NONE.getCode(),
                false, "");
    }


    public boolean isNonUniqueCodeAllow() {
        return preferences.getBoolean(KEY_IS_NON_UNIQUE_CODE_ALLOW, false);
    }

    public boolean isCheckCodeLength() {
        return preferences.getBoolean(KEY_IS_CHECK_CODE_LENGTH, false);
    }

    public int getCodeLength() {
        return preferences.getInt(KEY_CODE_LENGTH, 0);
    }

    public int getCodeLengthMAX() {
        return preferences.getInt(KEY_CODE_LENGTH_MAX, 0);
    }

    public int getCodeLengthMIN() {
        return preferences.getInt(KEY_CODE_LENGTH_MIN, 0);
    }


    public boolean isDoAdvancedFilter() {
        return preferences.getBoolean(KEY_ADVANCED_FILTER, false);
    }

    public String getCodePrefix() {
        return preferences.getString(KEY_PREFIX, "");
    }

    public String getCodeSuffix() {
        return preferences.getString(KEY_SUFFIX, "");
    }

    public String getCodeEnding() {
        return preferences.getString(KEY_ENDING, "");
    }


//    public Set<String> getCodeLabelType() {
//        return preferences.getStringSet(KEY_LABEL_TYPE, new HashSet<>());
//    }

    public String getCodeLabelType() {
        return preferences.getString(KEY_LABEL_TYPE, LabelType.NONE.getCode());
    }


    public boolean isServerConfigured() {
        return preferences.getBoolean(KEY_IS_SERVER_CONFIGURED, false);
    }


    public String getServerAddress() {
        return preferences.getString(KEY_SERVER_ADDRESS, "");
    }


}
