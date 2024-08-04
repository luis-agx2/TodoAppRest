package com.lag.todoapp.rest.todoapprest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDashboardDto {
    List<TaskDto> inProgress;

    List<TaskDto> news;

    List<TaskDto> completed;

    List<TaskDto> paused;

    List<TaskDto> cancelled;
}
