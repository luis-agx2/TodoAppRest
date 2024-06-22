package com.lag.todoapp.rest.todoapprest.dto.entrada;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterEntradaDto {
    private String username;
    private String email;
    private String password;
    private Long roleId;
}
