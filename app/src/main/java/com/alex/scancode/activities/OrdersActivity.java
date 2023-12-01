package com.alex.scancode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.alex.scancode.R;
import com.alex.scancode.db.RoomDB;
import com.alex.scancode.managers.SettingsManager;
import com.alex.scancode.managers.adapters.OrdersAdapter;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;

import java.util.List;

public class OrdersActivity extends AppCompatActivity implements OrdersAdapter.OnItemClickListener{
    private static final String TAG = "OrdersActivity";
    Context context;
    List<Order> orderList;
    RecyclerView recyclerView;
    OrdersAdapter orderAdapter;
    private SettingsManager settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        context = getApplicationContext();
        settings = new SettingsManager(this);

        // try to sent not synch orders to server if server configured
        if (settings.getIsAutoSynch()) syncOrdersWithServer();

        initializeRecyclerView();

        ImageView imageView = findViewById(R.id.your_image_view);
        imageView.setOnClickListener(v -> showPopupMenu(imageView));

    }

    private void initializeRecyclerView() {
        Log.i(TAG, "initializeRecyclerView: ");
        RoomDB roomDB = RoomDB.getInstance(context);
        recyclerView = findViewById(R.id.o_rv_orders);
        orderAdapter = new OrdersAdapter(roomDB.orderDAO().getAll(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderAdapter);
    }

    private void syncOrdersWithServer() {
        Toast.makeText(this, "Synchronization with server", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "syncOrdersWithServer: ");
        // logic will be here

        // add Toast for special cases like
        // 1 all orders synchronized
        // 2 serverIsNotConfigured
        // 3 no internet connection
        // 4 server is not found or answer
        // 5 something wrong
    }

    @Override
    public void onItemClick(int orderId) {
        Log.i(TAG, "onItemClick: " + orderId);
        Intent intent = new Intent(this, SpecialOrderActivity.class);
        intent.putExtra("orderId", orderId);
        startActivity(intent);
    }


    private void showPopupMenu(View view) {
        Log.i(TAG, "showPopupMenu: ");
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_orders, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId(); // Store the item ID in a final variable
            if (itemId == R.id.menu_synchOrders) {
                return true;
            } else return itemId == R.id.menu_clearOrders;
        });
        popupMenu.show();
    }



}