package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.RegisterDto;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public RegisterDto toRegisterSalidaDto(UserEntity userEntity) {
        TypeMap<UserEntity, RegisterDto> mapper = modelMapper.createTypeMap(UserEntity.class, RegisterDto.class);

        mapper.addMapping(UserEntity::getId, RegisterDto::setId);
        mapper.addMapping(UserEntity::getUserName, RegisterDto::setUsername);
        mapper.addMapping(UserEntity::getEmail, RegisterDto::setEmail);

        return mapper.map(userEntity);
    }
}
