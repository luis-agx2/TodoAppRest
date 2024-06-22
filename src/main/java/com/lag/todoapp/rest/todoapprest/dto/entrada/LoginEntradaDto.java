package com.lag.todoapp.rest.todoapprest.dto.entrada;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
