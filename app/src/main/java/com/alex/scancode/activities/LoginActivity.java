package com.alex.scancode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.scancode.R;
import com.alex.scancode.managers.Ans;
import com.alex.scancode.managers.SettingsMan;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText login_et_login, login_et_pw;
    private SettingsMan sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sm = new SettingsMan(this);

        login_et_login = findViewById(R.id.login_edit_text_login);
        login_et_pw = findViewById(R.id.login_edit_text_password);
        Button login_btn_do_login = findViewById(R.id.login_btn_do_login);

        login_btn_do_login.setOnClickListener(view -> {
            String login = login_et_login.getText().toString(),
                    password = login_et_pw.getText().toString();
            if (login.equals(sm.getLogin()) && password.equals(sm.getPassword())) {
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