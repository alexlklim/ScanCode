package com.alex.scancode.managers.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOrderManager extends SQLiteOpenHelper {
    private static final String TAG = "DBOrderManager";

    private Context context;
    public static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "scanner", TABLE_NAME = "orders",
            KEY_ID = "id", KEY_ORDER = "orderNumber";
    public DBOrderManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
        db.execSQL(QUERY_DROP_TABLE);
        onCreate(db);
    }
    public void clearTable(){
        Log.d(TAG, "clearTable: ");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(QUERY_DELETE_ALL);
        // clear all data from codes table
    }
    public Cursor getAllOrders(){
        Log.d(TAG, "getAllData: ");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(QUERY_SELECT_ALL, null);
        }
        Log.d(TAG, "getAllData: SQLiteDatabase=null");
        return cursor;
    }
    public void addOrderToDB(String orderNumber){
        Log.d(TAG, "addOrderToDB: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ORDER, orderNumber);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            Log.d(TAG, "addOrderToDB: Failed to save order");
        } else {
            Log.i(TAG, "addOrderToDB: Success to save order to DB");
        }
    }

    public Cursor getOrderIdByOrderNumber(String orderNumber) {
        Log.i(TAG, "getOrderIdByOrderNumber: orderNumber=" + orderNumber);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(QUERY_SELECT_ID_BY_ODER_NUMBER, new String[]{orderNumber});
        }
        Log.i(TAG, "getOrderIdByOrderNumber:  orderID=" + cursor.moveToFirst());
        return cursor;
    }

    public boolean checkIOrderExistByOrderNumber(String orderNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(QUERY_CHECK_IF_ORDER_EXISTS, new String[]{orderNumber});
            if (cursor != null && cursor.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }






    private static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ORDER + " TEXT);";
    private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String QUERY_DELETE_ALL = "DELETE FROM " + TABLE_NAME;
    private static final String QUERY_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String QUERY_SELECT_ID_BY_ODER_NUMBER = "SELECT "+ KEY_ID +
            " FROM " + TABLE_NAME + " WHERE " + KEY_ORDER + "=?";
    private static final String QUERY_CHECK_IF_ORDER_EXISTS = "SELECT * FROM " + TABLE_NAME +
            " WHERE " + KEY_ORDER + "=?";


}
