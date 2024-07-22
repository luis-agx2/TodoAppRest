package com.lag.todoapp.rest.todoapprest.dto.entrada;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceUpdateDto {
    private String dashBoardView;

    private String theme;
}
