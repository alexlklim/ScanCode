package com.alex.scancode.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alex.scancode.models.Order;

import java.util.List;

@Dao
public interface OrderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Order order);

    @Query("SELECT * FROM orders")
    List<Order> getAll();

//    @Query("SELECT id from orders WHERE orderNumber = :orderNumber")
//    String getOrderIDByOrderNumber(String orderNumber);

    @Delete
    void delete(Order order);


}
