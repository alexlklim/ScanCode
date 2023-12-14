package com.alex.scancode.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.scancode.R;
import com.alex.scancode.db.RoomDB;
import com.alex.scancode.managers.Ans;
import com.alex.scancode.managers.SettingsManager;
import com.alex.scancode.managers.SynchManager;
import com.alex.scancode.managers.adapters.SpecialOrderAdapter;
import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;

import java.util.List;

public class SpecialOrderActivity extends AppCompatActivity implements SpecialOrderAdapter.OnItemClickListener{
    private static final String TAG = "SpecialOrderActivity";
    List<Code> codeList;
    Order order;
    RoomDB roomDB;
    Context context;
    int orderId;
    Button s_btn_comeBack, s_btn_deleteOrder;
    ImageView so_iv_synchStatus, rv_so_iv_del;
    TextView so_tv_dateTime, so_tv_totalTime, so_tv_orderNumber;
    SettingsManager settingsManager;
    SpecialOrderAdapter specialOrderAdapter;

    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_special_order);
        settingsManager = new SettingsManager(this);
        roomDB = RoomDB.getInstance(context);
        checkIfOrderExist();
        initializeRecyclerView();

        order = roomDB.orderDAO().getOrderByOrderId(orderId);
        System.out.println(order);
        addButtonListener();
        initializeTextViews();
        if (order.getIsSynch() == 0){
            if (settingsManager.isAutoSynch()) synchWithServer();
        }
    }


    private void initializeTextViews() {
        so_tv_orderNumber = findViewById(R.id.so_tv_orderNumber);
        so_tv_dateTime = findViewById(R.id.so_tv_dateTime);
        so_tv_totalTime = findViewById(R.id.so_tv_totalTime);

        so_tv_orderNumber.setText("Order N. " + order.getOrderNumber());
        so_tv_dateTime.setText(order.getStartTime());
        so_tv_totalTime.setText(order.getTimer());


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

        if (settingsManager.isAllowEditingOrders()){
            s_btn_deleteOrder.setOnClickListener(view -> deleteSpecialOrder(orderId));
        } else {
            ViewGroup parent = (ViewGroup) s_btn_deleteOrder.getParent();
            if (parent != null) {
                parent.removeView(s_btn_deleteOrder);
            }
            s_btn_comeBack.setGravity(Gravity.CENTER); // Set gravity to center for the come back button

        }
    }

    private void deleteSpecialOrder(int orderId) {
        Log.i(TAG, "deleteSpecialOrder: ");
        roomDB.orderDAO().delete(roomDB.orderDAO().getOrderByOrderId(orderId));
        Log.d(TAG, "deleteSpecialOrder() returned: order deleted successfully");
        Ans.showToast(getString(R.string.toast_order_deleted_successfully), this);
        finish();
    }

    private void synchWithServer() {
        Log.i(TAG, "synchWithServer: ");

        if (order.getIsSynch() == 1){
            Ans.showToast(getString(R.string.toast_order_already_synch), this);
            return;
        }
        if (!settingsManager.isServerConfigured()){
            Ans.showToast(context.getString(R.string.toast_configure_server_at_first), context);
            return;
        }

        SynchManager synchManager = new SynchManager(context);

        if (!synchManager.checkCommon()) return;

        synchManager.syncOrderWithServer(order)
                .thenAccept(synchronizedSuccessfully -> {
                    // Code to execute after synchronization is complete
                    if (synchronizedSuccessfully) {
                        Log.i(TAG, "synchWithServer: order was updated");
                        order.setIsSynch(1);
                        roomDB.orderDAO().update(order);
                        if (roomDB.orderDAO().getOrderByOrderId(orderId).getIsSynch() != 0) {
                            so_iv_synchStatus.setImageResource(R.drawable.ic_synch);
                        }
                    } else {
                        Ans.showToast(getString(R.string.toast_something_wrong_with_server), this);
                    }
                });
    }

    private void initializeRecyclerView() {
        rv_so_iv_del = findViewById(R.id.rv_so_iv_del);
        RecyclerView recyclerView = findViewById(R.id.sc_rv_codes);
        codeList = roomDB.codeDAO().getAllByOrderID(orderId);
        specialOrderAdapter = new SpecialOrderAdapter(codeList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(specialOrderAdapter);
    }

    @Override
    public void onItemClick(Code code) {
        Log.i(TAG, "onItemClick: " + code);
        roomDB.codeDAO().delete(code);
        //
        if (codeList.size() == 1) showDialogConfirmationToDeleting(code);
        else {
            specialOrderAdapter.newCodeListAfterDeleting(code);
        }
//        if (!answer) deleteOrder(code); // if answer false it means that order dont have any others codes
        specialOrderAdapter.notifyDataSetChanged();
    }



    private void deleteOrder(Code code) {
        roomDB.orderDAO().delete(
                roomDB.orderDAO().getOrderByOrderId(code.getOrderID())
        );
        Log.i(TAG, "onItemClick: delete order becouse order is empty" + code);
    }


    private void checkIfOrderExist() {
        Log.i(TAG, "checkIfOrderExist: ");
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", -1);
        System.out.println(orderId);
        if (orderId == -1) {
            Ans.showToast(getString(R.string.toast_something_wrong), this);
            finish();
        }
    }


    private void showDialogConfirmationToDeleting(Code code) {
        Log.d(TAG, "showDialogConfirmationToDeleting: ");
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_confirmation, null);
        TextView d_tv_confirmation = dialog.findViewById(R.id.d_tv_confirmation);
        Button d_btn_yes = dialog.findViewById(R.id.d_btn_yes);
        Button d_btn_no = dialog.findViewById(R.id.d_btn_no);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog);
        d_tv_confirmation.setText(new StringBuilder(getString(R.string.toast_delete_last_code)));

        d_btn_yes.setOnClickListener(v -> {
            Log.d(TAG, "showDialogConfirmationToDeleting: d_btn_yes");
            deleteOrder(code);
            alertDialog.dismiss();
            finish();

        });
        d_btn_no.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog = builder.create();
        alertDialog.show();
    }



}