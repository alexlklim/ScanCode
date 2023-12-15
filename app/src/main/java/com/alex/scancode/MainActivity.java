package com.alex.scancode;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.scancode.activities.LoginActivity;
import com.alex.scancode.activities.OrdersActivity;
import com.alex.scancode.activities.ScanActivity;
import com.alex.scancode.db.RoomDB;
import com.alex.scancode.managers.Ans;
import com.alex.scancode.managers.LangMan;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button main_btn_go_scan, main_btn_go_orders;
    ImageButton main_btn_go_settings;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        // initialize the buttons
        main_btn_go_scan = findViewById(R.id.main_btn_go_scan);
        main_btn_go_orders = findViewById(R.id.main_btn_go_orders);
        main_btn_go_settings = findViewById(R.id.main_btn_go_settings);

        // Set OnClickListener for all buttons
        main_btn_go_scan.setOnClickListener(view -> {
            showInputDialog();
        });
        main_btn_go_orders.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, OrdersActivity.class)));
        main_btn_go_settings.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LangMan.onAttach(base));
    }


    private AlertDialog alertDialog;

    private void showInputDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_input_order_number, null);
        EditText scan_text_order_number = dialog.findViewById(R.id.scan_text_order_number);
        Button scan_btn_do_start_order = dialog.findViewById(R.id.scan_btn_do_start_order);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);

        scan_btn_do_start_order.setOnClickListener(view -> {
            Log.d(TAG, "onClick: scan_btn_do_start_order was pressed");
            String orderNumber = scan_text_order_number.getText().toString();

            if (orderNumber.isEmpty()) {
                Log.d(TAG, "onClick: orderNumber is empty");
                Ans.showToast(getString(R.string.toast_empty_order_number), context);
            } else if (RoomDB.getInstance(context).orderDAO().isOrderExist(orderNumber)) {
                Log.d(TAG, "onClick: orderNumber already exists");
                // check is this orderNumber is exist in DB
                Ans.showToast(getString(R.string.toast_order_number_already_exists), context);
            } else {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                alertDialog.dismiss();
                intent.putExtra("ORDER_NUMBER", orderNumber);
                startActivity(intent);
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }


}