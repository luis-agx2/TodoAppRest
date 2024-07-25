package com.lag.todoapp.rest.todoapprest.enums;

public enum TaskStatusEnum {
    NEW,
    COMPLETED,
    PAUSE,
    IN_PROGRESS,
    CANCELED;

    public static TaskStatusEnum fromString(String value) {
        return TaskStatusEnum.valueOf(value.toUpperCase());
    }
}
