package com.lag.todoapp.rest.todoapprest.dto.salida;

import com.lag.todoapp.rest.todoapprest.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterSalidaDto {
    private Long id;
    private String username;
    private String email;
}
