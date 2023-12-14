package com.alex.scancode.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alex.scancode.models.Order;

import java.util.List;

@Dao
public interface OrderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Order order);

    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Query("SELECT * FROM orders WHERE isSynch = 1")
    List<Order> getNonSynchOrders();

    @Query("SELECT * FROM orders WHERE orderNumber = :orderNumber")
    Order getOrderByOrderNumber(String orderNumber);

    @Query("SELECT * FROM orders WHERE id = :orderId")
    Order getOrderByOrderId(int orderId);

    @Query("SELECT EXISTS(SELECT 1 FROM orders WHERE orderNumber = :orderNumber LIMIT 1)")
    boolean isOrderExist(String orderNumber);



    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("DELETE FROM orders WHERE isSynch = 1")
    void deleteAllSynchOrders();


}
