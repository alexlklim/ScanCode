package com.alex.scancode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.scancode.R;
import com.alex.scancode.managers.Ans;
import com.alex.scancode.managers.SettingsManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText login_edit_text_login, login_edit_text_password;
    private SettingsManager settingsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settingsManager = new SettingsManager(this);

        login_edit_text_login = findViewById(R.id.login_edit_text_login);
        login_edit_text_password = findViewById(R.id.login_edit_text_password);
        Button login_btn_do_login = findViewById(R.id.login_btn_do_login);

        login_btn_do_login.setOnClickListener(view -> {
            String login = login_edit_text_login.getText().toString(),
                    password = login_edit_text_password.getText().toString();
            if (login.equals(settingsManager.getLogin()) && password.equals(settingsManager.getPassword())) {
                Log.d(TAG, "onCreate: checkLoginAndPassword: true");
                Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                finish();
                startActivity(intent);
            } else {
                Ans.showToast(getString(R.string.toast_wrong_login_pw), this);

                Log.d(TAG, "onCreate: checkLoginAndPassword: false");
            }
        });

    }
}