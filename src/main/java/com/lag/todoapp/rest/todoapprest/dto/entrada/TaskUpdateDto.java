package com.lag.todoapp.rest.todoapprest.dto.entrada;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TaskUpdateDto {
    private String name;

    private String description;

    private String status;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long userId;

    private Long categoryId;
}
