package com.alex.scancode.models.settings;

import com.alex.scancode.models.enums.LabelType;

public class Filter {
    private static int isDoFilter = 0; //false
    private int isNonUniqueCodeAllow = 0; //false
    private int isCheckCodeLength = 0; //false
    private int codeLength;
    private String prefix;
    private String suffix;
    private String ending;
    private String type;

    public Filter() {
    }

    @Override
    public String toString() {
        return "Filter{" +
                "isNonUniqueCodeAllow=" + isNonUniqueCodeAllow +
                ", isCheckCodeLength=" + isCheckCodeLength +
                ", codeLength=" + codeLength +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", ending='" + ending + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static int getIsDoFilter() {
        return isDoFilter;
    }

    public static void setIsDoFilter(int isDoFilter) {
        Filter.isDoFilter = isDoFilter;
    }

    public int getIsNonUniqueCodeAllow() {
        return isNonUniqueCodeAllow;
    }

    public void setIsNonUniqueCodeAllow(int isNonUniqueCodeAllow) {
        this.isNonUniqueCodeAllow = isNonUniqueCodeAllow;
    }

    public int getIsCheckCodeLength() {
        return isCheckCodeLength;
    }

    public void setIsCheckCodeLength(int isCheckCodeLength) {
        this.isCheckCodeLength = isCheckCodeLength;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
