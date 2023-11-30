package com.alex.scancode.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alex.scancode.models.Code;
import com.alex.scancode.models.Order;
import com.alex.scancode.utiles.Util;

@Database(entities = {Code.class, Order.class}, version = Util.DATABASE_VERSION, exportSchema = false)
public abstract class RoomDB extends RoomDatabase{
    private static RoomDB database;
    private static final String DATABASE_NAME = Util.DATABASE_NAME;

    public synchronized static RoomDB getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract CodeDAO codeDAO();
    public abstract OrderDAO orderDAO();
}
