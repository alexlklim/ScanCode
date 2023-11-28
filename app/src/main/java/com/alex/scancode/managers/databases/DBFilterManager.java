package com.alex.scancode.managers.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBFilterManager extends SQLiteOpenHelper {

    private static final String TAG = "DBFilterManager";
    private Context context;
    public static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "scanner", TABLE_NAME = "filter";
    private static final String KEY_ID = "id", KEY_DO_FILTER = "isDoFilter", KEY_IS_NON_UNIQUE_ALLOW = "isNonUniqueCodeAllow", KEY_IS_CHECK_CODE_LENGTH = "isCheckCodeLength",
            KEY_CODE_LENGTH = "codeLength", KEY_PREFIX = "prefix", KEY_SUFFIX = "suffix", KEY_ENDING = "ending", KEY_TYPE = "type";

    public DBFilterManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
