package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.MyUserDetails;
import com.lag.todoapp.rest.todoapprest.dto.UserDetailDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.UserDetailUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.UserDetailEntity;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.mapper.UserDetailMapper;
import com.lag.todoapp.rest.todoapprest.repository.UserDetailRepository;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.UserDetailService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilDetailServiceImpl implements UserDetailService {
    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;
    private final UserDetailMapper userDetailMapper;

    public UtilDetailServiceImpl(UserDetailRepository userDetailRepository, UserRepository userRepository, UserDetailMapper userDetailMapper) {
        this.userDetailRepository = userDetailRepository;
        this.userRepository = userRepository;
        this.userDetailMapper = userDetailMapper;
    }

    @Override
    public UserDetailDto getMe() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userRepository.findById(userDetails.getId()).orElseThrow();

        UserDetailEntity userDetailEntity = userDetailRepository.findById(user.getUserDetails().getId()).orElseThrow();

        return userDetailMapper.toDto(userDetailEntity);
    }

    @Transactional
    @Override
    public UserDetailDto updateMe(UserDetailUpdateDto userDetailUpdateDto) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findById(userDetails.getId()).orElseThrow();

        UserDetailEntity userDetailEntity = userDetailRepository.findById(userEntity.getUserDetails().getId()).orElseThrow();

        UserDetailEntity userDetailEntityToEdit = userDetailMapper.toEntityForEdit(userDetailEntity, userDetailUpdateDto);

        return userDetailMapper.toDto(userDetailRepository.save(userDetailEntityToEdit));
    }
}
