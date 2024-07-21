package com.lag.todoapp.rest.todoapprest.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskEntradaDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private Long userId;

    @NotNull
    private Long categoryId;
}
