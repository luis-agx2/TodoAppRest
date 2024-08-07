package com.lag.todoapp.rest.todoapprest.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntradaDto {
    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotNull
    private Long taskId;
}
