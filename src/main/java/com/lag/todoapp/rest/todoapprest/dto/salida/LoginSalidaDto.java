package com.lag.todoapp.rest.todoapprest.dto.salida;

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
public class LoginSalidaDto {
    private String jwt;
}
