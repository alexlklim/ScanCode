package com.alex.scancode.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.alex.scancode.managers.DateTimeMan;
import com.alex.scancode.utiles.Util;

import java.io.Serializable;


@Entity(tableName = Util.TABLE_NAME_CODE)
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
    @ColumnInfo(name = "orderID")
    private int orderID;



    //standard methods

    public Code() {
    }

    @Ignore
    public Code(String code, String labelType, String gps) {
        this.code = code;
        this.time = DateTimeMan.getCurrentTimeString();
        this.labelType = labelType.replace("LABEL-TYPE-", "");
        this.gps = gps;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", time='" + time + '\'' +
                ", labelType='" + labelType + '\'' +
                ", gps='" + gps + '\'' +
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

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
