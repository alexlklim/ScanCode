package com.alex.scancode.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.alex.scancode.managers.DateTimeManager;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "codes")
public class Code implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name ="time")
    private String time;
    @ColumnInfo(name = "labelType")
    private String labelType;
    @ColumnInfo(name = "gps")
    private String gps;
    @ColumnInfo(name = "isSent")
    private int isSent;
    @ColumnInfo(name = "orderID")
    private int orderID;



    //standard methods

    public Code() {
    }

    @Ignore
    public Code(String code, String labelType, String gps) {
        this.code = code;
        this.time = DateTimeManager.getCurrentTimeString();
        this.labelType = labelType.replace("LABEL-TYPE-", "");
        this.gps = gps;
        this.isSent = 0;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", time='" + time + '\'' +
                ", labelType='" + labelType + '\'' +
                ", gps='" + gps + '\'' +
                ", isSent=" + isSent +
                ", orderID=" + orderID +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public int getIsSent() {
        return isSent;
    }

    public void setIsSent(int isSent) {
        this.isSent = isSent;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
