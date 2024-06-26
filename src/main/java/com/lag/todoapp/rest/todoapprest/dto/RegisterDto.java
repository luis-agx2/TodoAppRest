package com.lag.todoapp.rest.todoapprest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterDto {
    private Long id;

    private String username;

    private String email;
}
