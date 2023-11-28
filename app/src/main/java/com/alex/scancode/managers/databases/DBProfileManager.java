package com.alex.scancode.managers.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alex.scancode.models.enums.Lang;

public class DBProfileManager extends SQLiteOpenHelper {
    private static final String TAG = "DBCodeManager";

    private Context context;
    public static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "scanner", TABLE_NAME = "profile";
    private static final String KEY_ID = "id", KEY_IDENTIFIER = "identifier", KEY_LOGIN = "login", KEY_PW = "pw", KEY_LANG = "lang";


    public DBProfileManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        db.execSQL(QUERY_CREATE_TABLE);
        initializeDBTableProfile(db); // Pass the database instance to the method
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
        db.execSQL(QUERY_DROP_TABLE);
        onCreate(db);
    }

    public void initializeDBTableProfile(SQLiteDatabase db) {
        Log.d(TAG, "initializeDBTableProfile: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IDENTIFIER, 0);
        contentValues.put(KEY_LOGIN, "admin");
        contentValues.put(KEY_PW, "admin");
        contentValues.put(KEY_LANG, String.valueOf(Lang.ENG));
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Log.d(TAG, "initializeDBTableProfile: Failed to initialized");
        } else {
            Log.i(TAG, "initializeDBTableProfile: Success of initialization");
        }
    }

    public void updateDBTableProfile(int identifier, String login, String pw, String lang){
        Log.d(TAG, "updateDBTableProfile: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.execSQL(QUERY_DELETE_ALL);
        contentValues.put(KEY_IDENTIFIER, identifier);
        contentValues.put(KEY_LOGIN, login);
        contentValues.put(KEY_PW, pw);
        contentValues.put(KEY_LANG, lang);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            initializeDBTableProfile(db);
            Log.d(TAG, "updateDBTableProfile: Failed to update. Come back to default settings");
        } else {
            Log.i(TAG, "updateDBTableProfile: Success of updating");
        }
    }

    public Cursor getDataFromDB(){
        Log.d(TAG, "getDataFromDB: ");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(QUERY_SELECT_ALL, null);
        }
        Log.i(TAG, "getDataFromDB:  profileID=" + cursor.moveToFirst());
        return cursor;
    }
    public boolean checkLoginAndPassword(String login, String password) {
        Log.d(TAG, "checkLoginAndPassword: ");
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_LOGIN};
        String selection = KEY_LOGIN + " = ? AND " + KEY_PW + " = ?";
        String[] selectionArgs = {login, password};
        String limit = "1"; // Limit the result to 1, as you're checking for existence only.
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);

        boolean loginAndPasswordMatch = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        return loginAndPasswordMatch;
    }




    private static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " + KEY_IDENTIFIER + " INTEGER, " + KEY_LOGIN + " TEXT, " +
            KEY_PW + " TEXT, " + KEY_LANG + " TEXT);";
    private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String QUERY_DELETE_ALL = "DELETE FROM " + TABLE_NAME;
    private static final String QUERY_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
