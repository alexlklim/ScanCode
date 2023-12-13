package com.alex.scancode.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileType {
    JSON,
    XML,
    TXT,
    EXE;

    public static List<String> getFileTypeList() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
