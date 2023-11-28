package com.alex.scancode.models.settings;

import com.alex.scancode.models.enums.LabelType;

public class Filter {
    private static int isDoFilter = 0; //false
    private static int isNonUniqueCodeAllow = 0; //false
    private static int isCheckCodeLength = 0; //false
    private static int codeLength;
    private static String prefix;
    private static String suffix;
    private static String ending;
    private static String type;

    public Filter() {
    }

    public static int getIsDoFilter() {
        return isDoFilter;
    }

    public static void setIsDoFilter(int isDoFilter) {
        Filter.isDoFilter = isDoFilter;
    }

    public static int getIsNonUniqueCodeAllow() {
        return isNonUniqueCodeAllow;
    }

    public static void setIsNonUniqueCodeAllow(int isNonUniqueCodeAllow) {
        Filter.isNonUniqueCodeAllow = isNonUniqueCodeAllow;
    }

    public static int getIsCheckCodeLength() {
        return isCheckCodeLength;
    }

    public static void setIsCheckCodeLength(int isCheckCodeLength) {
        Filter.isCheckCodeLength = isCheckCodeLength;
    }

    public static int getCodeLength() {
        return codeLength;
    }

    public static void setCodeLength(int codeLength) {
        Filter.codeLength = codeLength;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        Filter.prefix = prefix;
    }

    public static String getSuffix() {
        return suffix;
    }

    public static void setSuffix(String suffix) {
        Filter.suffix = suffix;
    }

    public static String getEnding() {
        return ending;
    }

    public static void setEnding(String ending) {
        Filter.ending = ending;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Filter.type = type;
    }
}
