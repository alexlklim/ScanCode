package com.alex.scancode.models.enums;

import java.util.ArrayList;
import java.util.List;

public enum LabelType {
    NONE,
    CODE39,
    CODABAR,
    CODE128,
    D2OF5,
    IATA2OF5,
    I2OF5,
    CODE93,
    UPCA,
    UPCE0,
    UPCE1,
    EAN8,
    EAN13,
    MSI,
    EAN128,
    TRIOPTIC39,
    BOOKLAND,
    COUPON,
    DATABAR_COUPON,
    ISBT128,
    CODE32,
    PDF417,
    MICROPDF,
    TLC39,
    CODE11,
    MAXICODE,
    DATAMATRIX,
    QRCODE,
    GS1_DATABAR,
    GS1_DATABAR_LIM,
    GS1_DATABAR_EXP,
    USPOSTNET,
    USPLANET,
    UKPOSTAL,
    JAPPOSTAL,
    AUSPOSTAL,
    DUTCHPOSTAL,
    FINNISHPOSTAL_4S,
    CANPOSTAL,
    CHINESE_2OF5,
    AZTEC,
    MICROQR,
    US4STATE,
    US4STATE_FICS,
    COMPOSITE_AB,
    COMPOSITE_C,
    WEBCODE,
    SIGNATURE,
    KOREAN_3OF5,
    MATRIX_2OF5,
    OCR,
    HANXIN,
    MAILMARK,
    MULTICODE_DATA_FORMAT,
    GS1_DATAMATRIX,
    GS1_QRCODE,
    DOTCODE,
    GRIDMATRIX,
    UNDEFINED;


    public static List<String> getLabelTypes() {
        List<String> meanings = new ArrayList<>();
        for (LabelType labelType : LabelType.values()) {
            meanings.add(labelType.name());
        }
        return meanings;
    }
}
