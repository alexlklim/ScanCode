package com.alex.scancode.models.settings;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "filter")
public class Filter {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "isNonUniqueCodeAllow")
    private int isNonUniqueCodeAllow = 0; //false

    @ColumnInfo(name = "isCheckCodeLength")
    private int isCheckCodeLength = 0; //false

    @ColumnInfo(name = "codeLength")
    private int codeLength;

    @ColumnInfo(name = "prefix")
    private String prefix;

    @ColumnInfo(name = "suffix")
    private String suffix;

    @ColumnInfo(name = "ending")
    private String ending;

    @ColumnInfo(name = "labelType")
    private String labelType;

    public Filter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "id=" + id +
                ", isNonUniqueCodeAllow=" + isNonUniqueCodeAllow +
                ", isCheckCodeLength=" + isCheckCodeLength +
                ", codeLength=" + codeLength +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", ending='" + ending + '\'' +
                ", labelType='" + labelType + '\'' +
                '}';
    }
}
