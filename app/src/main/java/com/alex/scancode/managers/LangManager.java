package com.alex.scancode.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LangManager {

    private Context ct;
    private SharedPreferences sharedPreferences;

    public LangManager(Context ctx){
        this.ct = ctx;
        sharedPreferences = ct.getSharedPreferences("LANG", Context.MODE_PRIVATE);
    }

    public void updateResource(String code){
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = ct.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        setLang(code);
    }
    public String getLang(){
        return sharedPreferences.getString("lang", "en");
    }

    public void setLang(String code){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", code);
        editor.commit();
    }
}
