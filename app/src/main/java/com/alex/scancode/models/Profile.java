package com.alex.scancode.models;

import com.alex.scancode.models.enums.Lang;

public class Profile {
    private static int identifier;
    private static String login = "admin";
    private static String pw = "admin";
    private static String lang = String.valueOf(Lang.ENG);
    private static int isDoFilter = 0;
    private static int isNonUniqueCodeAllow = 0;
    private static String config_ip;
    private static String config_port;
    private static String config_address;


    public Profile() {
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

    public static String getConfig_ip() {
        return config_ip;
    }

    public static void setConfig_ip(String config_ip) {
        Profile.config_ip = config_ip;
    }

    public static String getConfig_port() {
        return config_port;
    }

    public static void setConfig_port(String config_port) {
        Profile.config_port = config_port;
    }

    public static String getConfig_address() {
        return config_address;
    }

    public static void setConfig_address(String config_address) {
        Profile.config_address = config_address;
    }

    public static int getIsNonUniqueCodeAllow() {
        return isNonUniqueCodeAllow;
    }

    public static void setIsNonUniqueCodeAllow(int isNonUniqueCodeAllow) {
        Profile.isNonUniqueCodeAllow = isNonUniqueCodeAllow;
    }
}