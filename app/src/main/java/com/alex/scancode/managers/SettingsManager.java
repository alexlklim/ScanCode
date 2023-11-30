package com.alex.scancode.managers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;

import com.alex.scancode.models.enums.LabelType;
import com.alex.scancode.models.enums.Lang;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public int getIdentifier(){
        return preferences.getInt(KEY_ID, 100000);
    }
    public void setIdentifier(int identifier) {preferences.edit().putInt(KEY_ID, identifier).apply();}


    public String getLogin(){
        return preferences.getString(KEY_LOGIN, "admin");
    }
    public void setLogin(String login) {preferences.edit().putString(KEY_LOGIN, login).apply();}


    public String getPassword(){
        return preferences.getString(KEY_PW, "admin");
    }
    public void setPassword(String pw) {preferences.edit().putString(KEY_PW, pw).apply();}


    public String getLang(){
        return preferences.getString(KEY_LANG, "EN");
    }
    public void setLang(String lang) {preferences.edit().putString(KEY_LANG, lang).apply();}


    public boolean isDoFilter(){
        return preferences.getBoolean(KEY_IS_DO_FILTER, false);
    }
    public void setIsDoFilter(boolean bool) {preferences.edit().putBoolean(KEY_IS_DO_FILTER, bool).apply();}


    public boolean isNonUniqueCodeAllow() {return preferences.getBoolean(KEY_IS_NON_UNIQUE_CODE_ALLOW, false);}
    public void setIsNonUniqueCodeAllow(boolean bool) {preferences.edit().putBoolean(KEY_IS_NON_UNIQUE_CODE_ALLOW, bool).apply();}


    public boolean isCheckCodeLength(){return preferences.getBoolean(KEY_IS_CHECK_CODE_LENGTH, false);}
    public void setIsCheckCodeLength(boolean bool) {preferences.edit().putBoolean(KEY_IS_CHECK_CODE_LENGTH, bool).apply();}


    public int getCodeLength(){return preferences.getInt(KEY_CODE_LENGTH, 0);}
    public void setCodeLength(int length) {preferences.edit().putInt(KEY_CODE_LENGTH, length).apply();}


    public int getCodeLengthMAX(){return preferences.getInt(KEY_CODE_LENGTH_MAX, 0);}
    public void setCodeLengthMAX(int length) {preferences.edit().putInt(KEY_CODE_LENGTH_MAX, length).apply();}


    public int getCodeLengthMIN(){return preferences.getInt(KEY_CODE_LENGTH_MIN, 0);}
    public void setCodeLengthMIN(int length) {preferences.edit().putInt(KEY_CODE_LENGTH_MIN, length).apply();}


    public boolean isDoAdvancedFilter() {return preferences.getBoolean(KEY_ADVANCED_FILTER, false);}
    public void setIsDoAdvancedFilter(boolean bool) {preferences.edit().putBoolean(KEY_ADVANCED_FILTER, bool).apply();}


    public String getKeyPrefix(){return preferences.getString(KEY_PREFIX, "");}
    public void setKeyPrefix(String prefix) {preferences.edit().putString(KEY_PREFIX, prefix).apply();}


    public String getKeySuffix(){return preferences.getString(KEY_SUFFIX, "");}
    public void setKeySuffix(String suffix) {preferences.edit().putString(KEY_SUFFIX, suffix).apply();}


    public String getKeyEnding(){return preferences.getString(KEY_ENDING, "");}
    public void setKeyEnding(String ending) {preferences.edit().putString(KEY_ENDING, ending).apply();}


    public Set<String> getKeyLabelType(){return preferences.getStringSet(KEY_LABEL_TYPE, new HashSet<>());}
    public void setKeyLabelType(Set<String> labelTypes) {preferences.edit().putStringSet(KEY_LABEL_TYPE, labelTypes).apply();}


    public boolean isServerConfigured(){return preferences.getBoolean(KEY_IS_SERVER_CONFIGURED, false);}
    public void setIsServerConfigured(boolean bool) {preferences.edit().putBoolean(KEY_IS_SERVER_CONFIGURED, bool).apply();}


    public String getServerAddress(){return preferences.getString(KEY_SERVER_ADDRESS, "");}
    public void setServerAddress(String serverAddress) {preferences.edit().putString(KEY_SERVER_ADDRESS, serverAddress).apply();}
}
