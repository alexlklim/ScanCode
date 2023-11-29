package com.alex.scancode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.scancode.activities.LoginActivity;
import com.alex.scancode.activities.OrdersActivity;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    Button main_btn_go_scan, main_btn_go_orders, main_btn_go_exit;
    ImageButton main_btn_go_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the buttons
        main_btn_go_scan = findViewById(R.id.main_btn_go_scan);
        main_btn_go_orders = findViewById(R.id.main_btn_go_orders);
        main_btn_go_exit = findViewById(R.id.main_btn_go_exit);
        main_btn_go_settings = findViewById(R.id.main_btn_go_settings);

        // Set OnClickListener for all buttons
        main_btn_go_scan.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: main_btn_go_scan was pressed");
//            showInputDialog();
        });
        main_btn_go_orders.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: main_btn_go_orders was pressed");
            Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
            startActivity(intent);
        });

        main_btn_go_settings.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: main_btn_go_settings was pressed");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        main_btn_go_exit.setOnClickListener(view -> {
            Log.d(TAG, "onCreate: main_btn_go_exit was pressed");
            System.exit(0);
        });

    }

//    private AlertDialog alertDialog;
//    private void showInputDialog() {
//        LayoutInflater inflater = getLayoutInflater();
//        View dialog = inflater.inflate(R.layout.dialog_input_order_number, null);
//        EditText scan_text_order_number = dialog.findViewById(R.id.scan_text_order_number);
//        Button scan_btn_do_start_order = dialog.findViewById(R.id.scan_btn_do_start_order);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(dialog);
//
//        scan_btn_do_start_order.setOnClickListener(view -> {
//            Log.d(TAG, "onClick: scan_btn_do_start_order was pressed");
//            DBOrderManager dbManager = new DBOrderManager(MainActivity.this);
//            String orderNumber = scan_text_order_number.getText().toString();
//            if (orderNumber.isEmpty()){
//                Log.d(TAG, "onClick: orderNumber is empty");
//                Toast.makeText(MainActivity.this, "Order number could not be empty", Toast.LENGTH_SHORT).show();
//            } else if (dbManager.checkIOrderExistByOrderNumber(orderNumber)){
//                Log.d(TAG, "onClick: orderNumber already exists");
//                // check is this orderNumber is exist in DB
//                Toast.makeText(MainActivity.this, "Order number already exists", Toast.LENGTH_SHORT).show();
//            } else{
//                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
//                alertDialog.dismiss();
//                intent.putExtra("ORDER_NUMBER", orderNumber); // pass the data on
//                startActivity(intent);
//            }
//        });
//        alertDialog = builder.create();
//        alertDialog.show();
//    }



}