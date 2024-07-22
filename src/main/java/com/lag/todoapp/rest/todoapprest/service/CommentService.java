package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.CommentDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllByUserId();

    CommentDto createMe(CommentEntradaDto commentEntradaDto);

    CommentDto updateMe(CommentUpdateDto commentUpdateDto, Long commentId) throws AccessNotGrantedException;

    void deleteMe(Long commentId) throws AccessNotGrantedException;
}
