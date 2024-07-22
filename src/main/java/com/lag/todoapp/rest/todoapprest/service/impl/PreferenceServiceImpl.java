package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.MyUserDetails;
import com.lag.todoapp.rest.todoapprest.dto.PreferenceDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.PreferenceUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.PreferenceEntity;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import com.lag.todoapp.rest.todoapprest.mapper.PreferenceMapper;
import com.lag.todoapp.rest.todoapprest.repository.PreferenceRepository;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.PreferenceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class PreferenceServiceImpl implements PreferenceService {
    private final UserRepository userRepository;

    private final PreferenceRepository preferenceRepository;

    private final PreferenceMapper preferenceMapper;

    @Autowired
    public PreferenceServiceImpl(UserRepository userRepository, PreferenceRepository preferenceRepository, PreferenceMapper preferenceMapper) {
        this.userRepository = userRepository;
        this.preferenceRepository = preferenceRepository;
        this.preferenceMapper = preferenceMapper;
    }

    @Override
    public PreferenceDto getMe() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = userRepository.findById(userDetails.getId()).orElseThrow();
        PreferenceEntity preferences = preferenceRepository.findById(user.getPreferences().getId()).orElseThrow();

        return preferenceMapper.toDto(preferences);
    }

    @Transactional
    @Override
    public PreferenceDto updateMe(PreferenceUpdateDto preferenceUpdateDto) throws OptionNotFoundException {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = userRepository.findById(userDetails.getId()).orElseThrow();
        PreferenceEntity preferenceEntity = preferenceRepository.findById(user.getPreferences().getId()).orElseThrow();

        PreferenceEntity preferenceToEdit = preferenceMapper.toEntityForUpdate(preferenceEntity, preferenceUpdateDto);

        return preferenceMapper.toDto(preferenceRepository.save(preferenceToEdit));
    }
}
