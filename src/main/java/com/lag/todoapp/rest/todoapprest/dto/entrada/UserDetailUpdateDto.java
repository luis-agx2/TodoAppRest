package com.lag.todoapp.rest.todoapprest.dto.entrada;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailUpdateDto {
    private String firstName;

    private String lastNames;

    private String phone;

    private LocalDate dateOfBirth;
}
