package com.alex.scancode.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.R;
import com.alex.scancode.db.RoomDB;
import com.alex.scancode.managers.SynchManager;
import com.alex.scancode.managers.adapters.SpecialOrderAdapter;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;

import java.util.List;

public class SpecialOrderActivity extends AppCompatActivity {
    private static final String TAG = "SpecialOrderActivity";
    List<Code> codeList;
    Order order;
    RoomDB roomDB;
    Context context;
    int orderId;
    Button s_btn_comeBack, s_btn_deleteOrder;
    ImageView so_iv_synchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_special_order);
        roomDB = RoomDB.getInstance(context);
        checkIfOrderExist();
        initializeRecyclerView();

        order = roomDB.orderDAO().getOrderByOrderId(orderId);
        System.out.println(order);
        addButtonListener();
    }

    private void addButtonListener() {
        Log.i(TAG, "addButtonListener: ");
        s_btn_comeBack = findViewById(R.id.s_btn_comeBack);
        so_iv_synchStatus = findViewById(R.id.so_iv_synchStatus);
        s_btn_deleteOrder = findViewById(R.id.s_btn_deleteOrder);

        if (roomDB.orderDAO().getOrderByOrderId(orderId).getIsSynch() != 0) {
            so_iv_synchStatus.setImageResource(R.drawable.ic_synch);
        }


        s_btn_comeBack.setOnClickListener(view -> finish());
        so_iv_synchStatus.setOnClickListener(view -> synchWithServer());
        s_btn_deleteOrder.setOnClickListener(view -> deleteSpecialOrder(orderId));
    }

    private void deleteSpecialOrder(int orderId) {
        Log.i(TAG, "deleteSpecialOrder: ");
        roomDB.orderDAO().delete(roomDB.orderDAO().getOrderByOrderId(orderId));
        Log.d(TAG, "deleteSpecialOrder() returned: order deleted successfully");
        Toast.makeText(this, getString(R.string.toast_order_deleted_successfully), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void synchWithServer() {
        Log.i(TAG, "synchWithServer: ");

        SynchManager synchManager = new SynchManager(context);
        synchManager.syncOrderWithServer(order)
                .thenAccept(synchronizedSuccessfully -> {
                    // Code to execute after synchronization is complete
                    if (synchronizedSuccessfully) {
                        Log.i(TAG, "synchWithServer: order was updated");
                        if (roomDB.orderDAO().getOrderByOrderId(orderId).getIsSynch() != 0) {
                            so_iv_synchStatus.setImageResource(R.drawable.ic_synch);
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.toast_something_wrong_with_server), Toast.LENGTH_SHORT).show();
                    }
                });

        // logic if synch is not successful

    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.sc_rv_codes);
        codeList = roomDB.codeDAO().getAllByOrderID(orderId);
        SpecialOrderAdapter adapter = new SpecialOrderAdapter(codeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private void checkIfOrderExist() {
        Log.i(TAG, "checkIfOrderExist: ");
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", -1);
        System.out.println(orderId);
        if (orderId == -1) {
            Toast.makeText(this, "Something wring", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}