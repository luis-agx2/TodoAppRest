package com.lag.todoapp.rest.todoapprest.dto.entrada;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryUpdateDto {
    private String name;

    private String description;

    private String color;
}
