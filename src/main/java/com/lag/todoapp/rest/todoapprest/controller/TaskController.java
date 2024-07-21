package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.TaskDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.TaskUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<TaskDto>> getAllMe() {
        return ResponseEntity.ok(taskService.getAllMe());
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
    public ResponseEntity<TaskDto> updateMe(@PathVariable Long idTask, @RequestBody TaskUpdateDto taskUpdateDto) throws AccessNotGrantedException {
        return ResponseEntity.ok(taskService.updateMe(idTask, taskUpdateDto));
    }

    @DeleteMapping("/me/{idTask}")
    public ResponseEntity<Object> deleteMe(@PathVariable Long idTask) throws AccessNotGrantedException {
        taskService.deleteMe(idTask);
        return ResponseEntity.ok(null);
    }
}
