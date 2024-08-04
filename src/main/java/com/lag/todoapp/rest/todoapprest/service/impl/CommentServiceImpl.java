package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.CommentDto;
import com.lag.todoapp.rest.todoapprest.dto.MyUserDetails;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.CommentEntity;
import com.lag.todoapp.rest.todoapprest.entity.TaskEntity;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.mapper.CommentMapper;
import com.lag.todoapp.rest.todoapprest.repository.CommentRepository;
import com.lag.todoapp.rest.todoapprest.repository.TaskRepository;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;

    @Autowired
    public CommentServiceImpl(UserRepository userRepository, CommentRepository commentRepository, CommentMapper commentMapper, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<CommentDto> getAllByTaskId(Long taskId) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow();

        if (!userDetails.getId().equals(taskEntity.getUser().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        List<CommentEntity> commentEntities = commentRepository.findAllByTaskId(taskId);

        return commentMapper.toListDto(commentEntities);
    }

    @Transactional
    @Override
    public CommentDto createMe(CommentEntradaDto commentEntradaDto) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TaskEntity taskEntity = taskRepository.findById(commentEntradaDto.getTaskId()).orElseThrow();

        if (!userDetails.getId().equals(taskEntity.getUser().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        UserEntity userEntity = userRepository.findById(userDetails.getId()).orElseThrow();

        CommentEntity commentToAdd = commentMapper.entradaDtoToEntity(commentEntradaDto);

        commentToAdd.setAuthor(userEntity);
        commentToAdd.setTask(taskEntity);
        commentToAdd.setCreatedAt(LocalDateTime.now());

        return commentMapper.entityToDto(commentRepository.save(commentToAdd));
    }

    @Transactional
    @Override
    public CommentDto updateMe(CommentUpdateDto commentUpdateDto, Long commentId) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow();

        if (!userDetails.getId().equals(commentEntity.getAuthor().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        CommentEntity commentToEdit = commentMapper.toEntityForUpdate(commentEntity, commentUpdateDto);

        return commentMapper.entityToDto(commentRepository.save(commentToEdit));
    }

    @Transactional
    @Override
    public void deleteMe(Long commentId) throws AccessNotGrantedException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow();

        if (!userDetails.getId().equals(commentEntity.getAuthor().getId())) {
            throw new AccessNotGrantedException("Forbidden");
        }

        commentRepository.deleteById(commentId);
    }
}
