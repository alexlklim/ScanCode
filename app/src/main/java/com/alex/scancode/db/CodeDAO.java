package com.alex.scancode.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alex.scancode.models.Code;

import java.util.List;

@Dao
public interface CodeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Code code);

    @Query("SELECT * FROM codes")
    List<Code> getAll();

    @Query("SELECT * FROM codes WHERE orderID = :orderID")
    List<Code> getAllByOrderID(int orderID);

    @Delete
    void delete(Code code);
}
