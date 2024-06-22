package com.lag.todoapp.rest.todoapprest.dto.entrada;

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
public class LoginEntradaDto {
    private String email;
    private String password;
}
