package com.alex.scancode.models.settings;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alex.scancode.models.enums.Lang;

@Entity(tableName = "profile")
public class Profile {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "identifier")
    private static int identifier;

    @ColumnInfo(name = "login")
    private static String login;

    @ColumnInfo(name = "pw")
    private static String pw;

    @ColumnInfo(name = "lang")
    private static String lang = String.valueOf(Lang.ENG);

    @ColumnInfo(name = "isDoFilter")
    private static int isDoFilter;

    @ColumnInfo(name = "isServerConfigured")
    private static int isServerConfigured;





    public Profile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdentifier() {
        return identifier;
    }

    public static void setIdentifier(int identifier) {
        Profile.identifier = identifier;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Profile.login = login;
    }

    public static String getPw() {
        return pw;
    }

    public static void setPw(String pw) {
        Profile.pw = pw;
    }

    public static String getLang() {
        return lang;
    }

    public static void setLang(String lang) {
        Profile.lang = lang;
    }

    public static int getIsDoFilter() {
        return isDoFilter;
    }

    public static void setIsDoFilter(int isDoFilter) {
        Profile.isDoFilter = isDoFilter;
    }

    public static int getIsServerConfigured() {
        return isServerConfigured;
    }

    public static void setIsServerConfigured(int isServerConfigured) {
        Profile.isServerConfigured = isServerConfigured;
    }
}