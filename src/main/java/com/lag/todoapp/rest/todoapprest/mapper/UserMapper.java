package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.entrada.RegisterEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.salida.RegisterSalidaDto;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public RegisterSalidaDto toRegisterSalidaDto(UserEntity userEntity) {
        TypeMap<UserEntity, RegisterSalidaDto> mapper = modelMapper.createTypeMap(UserEntity.class, RegisterSalidaDto.class);

        mapper.addMapping(UserEntity::getId, RegisterSalidaDto::setId);
        mapper.addMapping(UserEntity::getUserName, RegisterSalidaDto::setUsername);
        mapper.addMapping(UserEntity::getEmail, RegisterSalidaDto::setEmail);

        return mapper.map(userEntity);
    }
}
