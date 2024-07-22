package com.lag.todoapp.rest.todoapprest.enums;

public enum DashboardEnum {
    GRID,
    LIST;

    public static DashboardEnum fromString(String value) {
        return DashboardEnum.valueOf(value.toUpperCase());
    }
}
