package com.alex.scancode.managers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AppCompat extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LangManager langManager = new LangManager(this);
        langManager.updateResource(langManager.getLang());
    }
}
