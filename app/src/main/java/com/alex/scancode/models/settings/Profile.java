package com.alex.scancode.models.settings;

import com.alex.scancode.models.enums.Lang;

public class Profile {
    private static int identifier;
    private static String login;
    private static String pw;
    private static String lang = String.valueOf(Lang.ENG);





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
}