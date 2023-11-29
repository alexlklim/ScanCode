package com.alex.scancode.managers.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alex.scancode.utiles.Util;

import java.util.Random;

public class DBCodeManager extends SQLiteOpenHelper {
    private static final String TAG = "DBCodeManager";

    private Context context;
    private static final String TABLE_NAME = Util.TABLE_NAME_CODE,
            KEY_ID = "id", KEY_CODE = "code", KEY_TIME = "time", KEY_TYPE = "type",
            KEY_GPS = "gps", KEY_IS_SENT = "isSent", KEY_ORDER = "orderNumber";

    public DBCodeManager(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null,  Util.DATABASE_VERSION);
        this.context = context;
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

    public void addCodeToDB(String code, String time, String type, String gps, int isSent, int orderNumber){
        Log.d(TAG, "addCodeToDB: " + code);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CODE, code);
        contentValues.put(KEY_TIME, time);
        contentValues.put(KEY_TYPE, type);
        contentValues.put(KEY_GPS, gps);
        contentValues.put(KEY_IS_SENT, isSent);
        contentValues.put(KEY_ORDER, orderNumber);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            Log.d(TAG, "addCodeToDB: Failed to save code");
        } else {
            Log.d(TAG, "addCodeToDB: Success to save code");
        }
    }



    private static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_CODE + " TEXT, " +
            KEY_TIME + " TEXT, " +
            KEY_TYPE + " TEXT, " +
            KEY_GPS + " TEXT, " +
            KEY_IS_SENT + " INTEGER, " +
            KEY_ORDER + " INTEGER);";

    private static final String QUERY_DROP_TABLE ="DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String QUERY_DELETE_ALL = "DELETE FROM " + TABLE_NAME;
    private static final String QUERY_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String QUERY_SELECT_CODES_BY_ODER =
            "SELECT "+ KEY_CODE + ", " + KEY_TIME+ ", " + KEY_TYPE + ", " + KEY_GPS + ", " + KEY_IS_SENT + ", " +
            " FROM " + TABLE_NAME + " WHERE " + KEY_ORDER + "=?";;


}
