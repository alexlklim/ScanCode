package com.alex.scancode.models.enums;

import java.util.ArrayList;
import java.util.List;

public enum Lang {
    ENG("English"),
    PL("Polish"),
    UA("Ukrainian");

    private final String displayName;

    Lang(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<String> getAllLang() {
        List<String> valuesList = new ArrayList<>();
        for (Lang lang : Lang.values()) {
            valuesList.add(lang.getDisplayName());
        }
        return valuesList;
    }
}
