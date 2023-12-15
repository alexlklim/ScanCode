package com.alex.scancode.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.scancode.models.enums.FileType;
import com.alex.scancode.models.enums.LabelType;
import com.alex.scancode.models.enums.Lang;
import com.alex.scancode.utiles.Util;

public class SettingsMan extends AppCompatActivity {
    private static final String TAG = "SettingsManager";

    private SharedPreferences preferences;
    public static final String PREF_NAME = "ScanApp";

    private static final String KEY_LANG = "lang", KEY_LOGIN = "login", KEY_PW = "pw", KEY_IS_NON_UNIQUE_CODE_ALLOW = "isNonUniqueCodeAllow",

    KEY_IS_CHECK_CODE_LENGTH = "isCheckCodeLength", KEY_CODE_LENGTH = "codeLength", KEY_CODE_LENGTH_MAX = "codeLengthMAX", KEY_CODE_LENGTH_MIN = "codeLengthMIN",

    KEY_IS_ADVANCED_FILTER = "advancedFilter", KEY_PREFIX = "prefix", KEY_SUFFIX = "suffix", KEY_ENDING = "ending", KEY_LABEL_TYPE = "labelType",

    KEY_IS_SERVER_CONFIGURED = "isServerConfigured", KEY_ID = "identifier", KEY_SERVER_ADDRESS = "serverAddress", KEY_IS_AUTO_SYNCH = "isAutoSynch",

    KEY_IS_ALLOW_EDITING_DURING_SCAN = "isAllowEditingDuringScan", KEY_IS_ALLOW_EDITING_ORDERS = "isAllowEditingOrders", KEY_IS_ADD_LOCATION_TO_CODE = "isAddLocationToCode",

    KEY_FILE_TYPE = "fileType";



    public SettingsMan(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void comeBackToDefaultSettings() {
        Log.i(TAG, "comeBackToDefaultSettings: ");
        setProfileSection("admin", "admin");
        setLanguage(Lang.English);
        setFilterConfig(false, false, 0, 0, 0,
                false, "", "","", LabelType.NONE.getCode());
        setServerConfig(false, false, "", 0 );
        setAdminConfig(false, false, true);
        setSavingConfig(FileType.JSON);

    }

    public void setProfileSection(String login, String pw) {
        preferences.edit().putString(KEY_LOGIN, login).apply();
        preferences.edit().putString(KEY_PW, pw).apply();
    }
    public void setLanguage(Lang lang) {
        preferences.edit().putString(KEY_LANG, lang.name()).apply();
    }

    public void setFilterConfig(boolean isNonUniqueCodeAllow, boolean isCheckCodeLength, int length, int lengthMIN, int lengthMAX,
                                 boolean isDoAdvancedFilter, String prefix, String suffix, String ending, String labelType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_IS_NON_UNIQUE_CODE_ALLOW, isNonUniqueCodeAllow)
                .putBoolean(KEY_IS_CHECK_CODE_LENGTH, isCheckCodeLength)
                .putInt(KEY_CODE_LENGTH, length).putInt(KEY_CODE_LENGTH_MIN, lengthMIN).putInt(KEY_CODE_LENGTH_MAX, lengthMAX)

                .putBoolean(KEY_IS_ADVANCED_FILTER, isDoAdvancedFilter)
                .putString(KEY_PREFIX, prefix).putString(KEY_SUFFIX, suffix).putString(KEY_ENDING, ending)
//                .putStringSet(KEY_LABEL_TYPE, labelTypes)
                .putString(KEY_LABEL_TYPE, labelType)

                .apply();
    }

    public void setServerConfig(boolean isServerConfigured, boolean autoSynch, String addressServer, int identifier) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_IS_SERVER_CONFIGURED, isServerConfigured)
                .putString(KEY_SERVER_ADDRESS, addressServer)
                .putInt(KEY_ID, identifier)
                .putBoolean(KEY_IS_AUTO_SYNCH, autoSynch)
                .apply();
    }
    public void setAdminConfig(boolean isAllowEditingDuringScan, boolean isAllowEditingOrders,
                               boolean isAddLocationToCode ) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_IS_ALLOW_EDITING_DURING_SCAN, isAllowEditingDuringScan)
                .putBoolean(KEY_IS_ALLOW_EDITING_ORDERS, isAllowEditingOrders)
                .putBoolean(KEY_IS_ADD_LOCATION_TO_CODE, isAddLocationToCode)
                .apply();
    }

    public void setSavingConfig(FileType fileType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_FILE_TYPE, fileType.name())
                .apply();
    }

    // for profile section
    public String getLogin() {
        return preferences.getString(KEY_LOGIN, "admin");
    }

    public String getPassword() {
        return preferences.getString(KEY_PW, "admin");
    }

    public String getLang() {
        return preferences.getString(KEY_LANG, Lang.English.name());
    }





    // for filtering
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
        return preferences.getBoolean(KEY_IS_ADVANCED_FILTER, false);
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



    // for serrver section
    public boolean isServerConfigured() {
        return preferences.getBoolean(KEY_IS_SERVER_CONFIGURED, false);
    }
    public boolean isAutoSynch() {
        return preferences.getBoolean(KEY_IS_AUTO_SYNCH, false);
    }

    public String getServerAddress() {
        return preferences.getString(KEY_SERVER_ADDRESS, Util.URL);
    }

    public int getIdentifier() {
        return preferences.getInt(KEY_ID, 100000);
    }


    // for admin section
    public boolean isAllowEditingDuringScan() {
        return preferences.getBoolean(KEY_IS_ALLOW_EDITING_DURING_SCAN, false);
    }
    public boolean isAllowEditingOrders() {
        return preferences.getBoolean(KEY_IS_ALLOW_EDITING_ORDERS, false);
    }

    public boolean isAddLocationToCode() {
        return preferences.getBoolean(KEY_IS_ADD_LOCATION_TO_CODE, true);
    }



    // for saving files
    public String getFileType() {
        return preferences.getString(KEY_FILE_TYPE, "NONE");
    }


}
