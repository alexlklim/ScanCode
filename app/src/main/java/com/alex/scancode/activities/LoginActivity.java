package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.alex.scancode.R;
import com.alex.scancode.managers.databasesSQLite.DBProfileManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText login_edit_text_login, login_edit_text_password;
    private Button login_btn_do_login;
    private DBProfileManager dbProfileManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_edit_text_login = findViewById(R.id.login_edit_text_login);
        login_edit_text_password = findViewById(R.id.login_edit_text_password);
        login_btn_do_login = findViewById(R.id.login_btn_do_login);

        login_btn_do_login.setOnClickListener(view -> {
            String login = login_edit_text_login.getText().toString();
            String password = login_edit_text_password.getText().toString();
            dbProfileManager = new DBProfileManager(LoginActivity.this);
            if (!dbProfileManager.checkLoginAndPassword(login, password)){
                Log.d(TAG, "onCreate: checkLoginAndPassword: true");
                Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Log.d(TAG, "onCreate: checkLoginAndPassword: false");
            }
        });

    }
}