package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.TaskDashboardDto;
import com.lag.todoapp.rest.todoapprest.dto.TaskDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import com.lag.todoapp.rest.todoapprest.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/me")
    public ResponseEntity<Page<TaskDto>> getAllMe(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(taskService.getAllMe(page, size));
    }

    @GetMapping("/me/dashboard")
    public ResponseEntity<TaskDashboardDto> getMeDashboard() {
        return ResponseEntity.ok(taskService.getAllMeDashboard());
    }

    @GetMapping("/me/{idTask}")
    public ResponseEntity<TaskDto> getOneMe(@PathVariable Long idTask) throws AccessNotGrantedException {
        return ResponseEntity.ok(taskService.getOneMe(idTask));
    }

    @PostMapping("/me")
    public ResponseEntity<TaskDto> createMe(@RequestBody @Valid TaskEntradaDto taskEntradaDto) throws AccessNotGrantedException {
        return ResponseEntity.ok(taskService.createMe(taskEntradaDto));
    }

    @PutMapping("/me/{idTask}")
    public ResponseEntity<TaskDto> updateMe(@PathVariable Long idTask, @RequestBody TaskUpdateDto taskUpdateDto) throws AccessNotGrantedException, OptionNotFoundException {
        return ResponseEntity.ok(taskService.updateMe(idTask, taskUpdateDto));
    }

    @DeleteMapping("/me/{idTask}")
    public ResponseEntity<Object> deleteMe(@PathVariable Long idTask) throws AccessNotGrantedException {
        taskService.deleteMe(idTask);
        return ResponseEntity.ok(null);
    }
}
