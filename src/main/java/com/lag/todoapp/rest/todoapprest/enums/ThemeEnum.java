package com.lag.todoapp.rest.todoapprest.enums;

public enum ThemeEnum {
    DARK,
    LIGHT;

    public static ThemeEnum fromString(String value) {
        return ThemeEnum.valueOf(value.toUpperCase());
    }
}
