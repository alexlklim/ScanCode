package com.alex.scancode.models;

import android.icu.text.SimpleDateFormat;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;


public class Code {
    private String code;
    private String time;
    private String type;
    private String gps;
    private int isSent;
    private int isVisible;
    private int orderID;



    //standard methods



    public Code(String code, String type, List<Double> gps) {
        this.code = code;
        this.time = getCurrentTimeString();
        this.type = type.substring("LABEL-TYPE-".length());
        this.gps = convertGpsToString(gps);
        this.isSent = 0;
        this.isVisible = 0;
    }

    private String getCurrentTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
    private String convertGpsToString(List<Double> gps) {
        if (gps != null && !gps.isEmpty()) {
            return TextUtils.join(",", gps);
        }
        return "";
    }


    @NonNull
    @Override
    public String toString() {
        return "Code{code='" + code + ", time='" + time + ", type='" + type + ", gps='" + gps +
                ", isSent=" + isSent + ", isVisible=" + isVisible + ", orderNumber='" + orderID + '}';
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
