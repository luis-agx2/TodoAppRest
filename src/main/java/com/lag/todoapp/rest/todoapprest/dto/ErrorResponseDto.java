package com.lag.todoapp.rest.todoapprest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    private Integer code;

    private String message;
}
