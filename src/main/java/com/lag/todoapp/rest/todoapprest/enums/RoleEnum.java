package com.lag.todoapp.rest.todoapprest.enums;

public enum RoleEnum {
    ADMIN(1L),
    USER(2L);

    private Long roleId;

    private RoleEnum(Long roleId) {
        this.roleId = roleId;
    }
}
