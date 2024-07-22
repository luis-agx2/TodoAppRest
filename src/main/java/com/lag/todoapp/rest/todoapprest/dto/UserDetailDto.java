package com.lag.todoapp.rest.todoapprest.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailDto {
    private String firstName;

    private String lastNames;

    private String phone;

    private LocalDate dateOfBirth;
}
