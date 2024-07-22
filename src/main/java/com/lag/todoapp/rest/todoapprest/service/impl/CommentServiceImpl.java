package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.CommentDto;
import com.lag.todoapp.rest.todoapprest.dto.MyUserDetails;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.CommentUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.CommentEntity;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.mapper.CommentMapper;
import com.lag.todoapp.rest.todoapprest.repository.CommentRepository;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(UserRepository userRepository, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentDto> getAllByUserId() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<CommentEntity> commentEntities = commentRepository.findAllByAuthorId(userDetails.getId());

        return commentMapper.toListDto(commentEntities);
    }

    @Transactional
    @Override
    public CommentDto createMe(CommentEntradaDto commentEntradaDto) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = userRepository.findById(userDetails.getId()).orElseThrow();

        CommentEntity commentToAdd = commentMapper.entradaDtoToEntity(commentEntradaDto);
        commentToAdd.setAuthor(user);
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
