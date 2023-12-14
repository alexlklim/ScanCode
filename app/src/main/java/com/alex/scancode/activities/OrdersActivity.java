package com.alex.scancode.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.R;
import com.alex.scancode.db.RoomDB;
import com.alex.scancode.managers.Ans;
import com.alex.scancode.managers.SettingsManager;
import com.alex.scancode.managers.SynchManager;
import com.alex.scancode.managers.adapters.OrdersAdapter;
import com.alex.scancode.models.Order;
import com.alex.scancode.models.json.OrderWithCodes;
import com.alex.scancode.models.json.OrdersList;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity implements OrdersAdapter.OnItemClickListener {
    private static final String TAG = "OrdersActivity";
    Context context;
    List<Order> orderList;
    RecyclerView recyclerView;
    OrdersAdapter orderAdapter;
    SynchManager synchManager;
    RoomDB roomDB;
    SettingsManager sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        roomDB = RoomDB.getInstance(this);
        context = getApplicationContext();
        sm = new SettingsManager(this);
        synchManager = new SynchManager(this);

        initializeRecyclerView();

        ImageView imageView = findViewById(R.id.your_image_view);
        imageView.setOnClickListener(v -> showPopupMenu(imageView));

        if (sm.isAutoSynch()) synchWithServer();
    }

    private void synchWithServer() {
        Log.i(TAG, "synchWithServer: Sycnh With server!!!");

        if (!synchManager.synchNotSynchOrders()){
            return;
        }
        List<OrderWithCodes> orderWithCodesList = synchManager.getNotSynchOrdersWithCodes();
        List<Order> orders = new ArrayList<>();
        for (OrderWithCodes orderWithCodes: orderWithCodesList){
            Order order = roomDB.orderDAO().getOrderByOrderNumber(orderWithCodes.getOrder().getOrderNumber());
            order.setIsSynch(1);
            roomDB.orderDAO().update(order);
        }
        orderAdapter.notifyDataSetChanged();
        initializeRecyclerView();

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
                synchManager.synchNotSynchOrders();

                return true;
            } else if (itemId == R.id.menu_clearOrders) {
                synchManager.clearSynchOrders();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeRecyclerView();
        // This is called when the activity is about to be displayed

        // Print or log information about what happened
        Log.i("YourPreviousActivity", "Returned to YourPreviousActivity. Something happened.");
    }


}