package com.alex.scancode.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBCodeManager extends SQLiteOpenHelper {
    private Context context;
    public static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "scanner";
    private static final String TABLE_NAME = "codes";
    private static final String KEY_ID = "id", KEY_CODE = "code", KEY_TIME = "time", KEY_TYPE = "type", KEY_GPS = "gps",
            KEY_IS_SENT = "isSent", KEY_IS_VISIBLE = "isVisible", KEY_ORDER_NUMBER = "orderNumber";
    public DBCodeManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
