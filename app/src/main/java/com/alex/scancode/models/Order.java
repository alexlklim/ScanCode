package com.alex.scancode.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.alex.scancode.utiles.Util;

@Entity(tableName = Util.TABLE_NAME_ORDER)
public class Order {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "orderNumber")
    private String orderNumber;

    @ColumnInfo(name = "startTime")
    private String startTime;

    @ColumnInfo(name = "timer")
    private String timer;

    @ColumnInfo(name = "isSynch")
    private int isSynch;

    public Order() {
    }

    @Ignore
    public Order(String orderNumber, String startTime, String timer) {
        this.orderNumber = orderNumber;
        this.startTime = startTime;
        this.timer = timer;
        this.isSynch = 0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", startTime='" + startTime + '\'' +
                ", timer='" + timer + '\'' +
                ", isSynch=" + isSynch +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getIsSynch() {
        return isSynch;
    }

    public void setIsSynch(int isSynch) {
        this.isSynch = isSynch;
    }
}
