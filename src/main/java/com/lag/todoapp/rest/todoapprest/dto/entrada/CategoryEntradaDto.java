package com.lag.todoapp.rest.todoapprest.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryEntradaDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String color;
}
