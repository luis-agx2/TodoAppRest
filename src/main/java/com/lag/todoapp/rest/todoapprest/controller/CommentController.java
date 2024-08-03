package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.CommentDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/me/{taskId}")
    public ResponseEntity<List<CommentDto>> getAllMe(@PathVariable Long taskId) throws AccessNotGrantedException {
        return ResponseEntity.ok(commentService.getAllByTaskId(taskId));
    }

    @PostMapping("/me")
    public ResponseEntity<CommentDto> createMe(@Valid @RequestBody CommentEntradaDto commentEntradaDto) throws AccessNotGrantedException {
        return ResponseEntity.ok(commentService.createMe(commentEntradaDto));
    }

    @PutMapping("/me/{commentId}")
    public ResponseEntity<CommentDto> updateMe(@Valid @RequestBody CommentUpdateDto commentUpdateDto, @PathVariable Long commentId) throws AccessNotGrantedException {
        return ResponseEntity.ok(commentService.updateMe(commentUpdateDto, commentId));
    }

    @DeleteMapping("/me/{commentId}")
    public ResponseEntity<Object> deleteMe(@PathVariable Long commentId) throws AccessNotGrantedException {
        commentService.deleteMe(commentId);
        return ResponseEntity.ok(null);
    }
}
