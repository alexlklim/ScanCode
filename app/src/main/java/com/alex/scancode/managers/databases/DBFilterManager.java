package com.alex.scancode.managers.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alex.scancode.models.enums.Lang;
import com.alex.scancode.models.settings.Filter;
import com.alex.scancode.utiles.Util;

public class DBFilterManager extends SQLiteOpenHelper {

    private static final String TAG = "DBFilterManager";
    private Context context;
    private static final String TABLE_NAME = Util.TABLE_NAME_FILTER;
    private static final String KEY_ID = "id", KEY_DO_FILTER = "isDoFilter", KEY_IS_NON_UNIQUE_ALLOW = "isNonUniqueCodeAllow", KEY_IS_CHECK_CODE_LENGTH = "isCheckCodeLength",
            KEY_CODE_LENGTH = "codeLength", KEY_PREFIX = "prefix", KEY_SUFFIX = "suffix", KEY_ENDING = "ending", KEY_TYPE = "type";

    public DBFilterManager(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        db.execSQL(QUERY_CREATE_TABLE);
        updateDBTableFilter(0,0, 0, 0, "", "", "", String.valueOf(Lang.ENG));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
        db.execSQL(QUERY_DROP_TABLE);
        onCreate(db);

    }
    public void updateDBTableFilter(int doFilter, int isNonUniqueAllow, int isCheckCodeLength, int codeLength,
                                    String prefix, String suffix, String ending, String labelType){
        Log.d(TAG, "updateDBTableFilter: ");
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(QUERY_DELETE_ALL);
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DO_FILTER, doFilter);
        contentValues.put(KEY_IS_NON_UNIQUE_ALLOW, isNonUniqueAllow);
        contentValues.put(KEY_IS_CHECK_CODE_LENGTH, isCheckCodeLength);
        contentValues.put(KEY_CODE_LENGTH, codeLength);
        contentValues.put(KEY_PREFIX, prefix);
        contentValues.put(KEY_SUFFIX, suffix);
        contentValues.put(KEY_ENDING, ending);
        contentValues.put(KEY_TYPE, labelType);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Log.d(TAG, "initializeDBTableFilter: Failed to initialized");
        } else {
            Log.i(TAG, "initializeDBTableFilter: Success of initialization");
        }
    }
    public boolean isDoFilterEnabled() {
        Log.d(TAG, "isDoFilterEnabled: ");
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_DO_FILTER};
        String selection = KEY_ID + " = ?";
        String[] selectionArgs = {"1"}; // Assuming there's only one row in the table
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean doFilterEnabled = false;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int doFilterValue = cursor.getInt(cursor.getColumnIndex(KEY_DO_FILTER));
            doFilterEnabled = (doFilterValue == 1);
        }
        cursor.close();
        db.close();
        return doFilterEnabled;
    }
    @SuppressLint("Range")
    public Filter getFilterData() {
        Log.d(TAG, "getFilterData: ");
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_DO_FILTER, KEY_IS_NON_UNIQUE_ALLOW, KEY_IS_CHECK_CODE_LENGTH,
                KEY_CODE_LENGTH, KEY_PREFIX, KEY_SUFFIX, KEY_ENDING, KEY_TYPE};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        Filter filter = new Filter();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Filter.setIsDoFilter(cursor.getInt(cursor.getColumnIndex(KEY_DO_FILTER)));
                filter.setIsNonUniqueCodeAllow(cursor.getInt(cursor.getColumnIndex(KEY_IS_NON_UNIQUE_ALLOW)));
                filter.setIsCheckCodeLength(cursor.getInt(cursor.getColumnIndex(KEY_IS_CHECK_CODE_LENGTH)));
                filter.setCodeLength(cursor.getInt(cursor.getColumnIndex(KEY_CODE_LENGTH)));
                filter.setPrefix(cursor.getString(cursor.getColumnIndex(KEY_PREFIX)));
                filter.setSuffix(cursor.getString(cursor.getColumnIndex(KEY_SUFFIX)));
                filter.setEnding(cursor.getString(cursor.getColumnIndex(KEY_ENDING)));
                filter.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
            }
        }
        cursor.close();
        db.close();
        return filter;
    }



    private static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_DO_FILTER + " INTEGER, " +
            KEY_IS_NON_UNIQUE_ALLOW + " INTEGER, " +
            KEY_IS_CHECK_CODE_LENGTH + " INTEGER, " +
            KEY_CODE_LENGTH + " INTEGER, " +
            KEY_PREFIX + " TEXT, " +
            KEY_SUFFIX + " TEXT, " +
            KEY_ENDING + " TEXT, " +
            KEY_TYPE + " TEXT);";
    private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String QUERY_DELETE_ALL = "DELETE FROM " + TABLE_NAME;
    private static final String QUERY_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
