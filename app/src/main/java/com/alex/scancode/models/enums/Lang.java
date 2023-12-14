package com.alex.scancode.models.enums;

import java.util.ArrayList;
import java.util.List;

public enum Lang {
    English("en"),
    Polish("pl"),
    Ukrainian("uk");

    private final String code;

    Lang(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static List<String> getAllLang() {
        List<String> names = new ArrayList<>();
        for (Lang lang : values()) {
            names.add(lang.name());
        }
        return names;
    }

    public static String getCodeByName(String langName) {
        for (Lang lang : values()) {
            if (lang.name().equalsIgnoreCase(langName)) {
                return lang.getCode();
            }
        }
        // Return null or throw an exception based on your requirement
        return null;
    }
}
