package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.UserDetailDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.UserDetailUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.UserDetailEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class UserDetailMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    TypeMap<UserDetailEntity, UserDetailDto> mapperToDto = modelMapper.createTypeMap(UserDetailEntity.class, UserDetailDto.class);
    TypeMap<UserDetailEntity, UserDetailEntity> mapperToEntity = modelMapper.createTypeMap(UserDetailEntity.class, UserDetailEntity.class);

    public UserDetailMapper() {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public UserDetailDto toDto(UserDetailEntity userDetail) {
        return mapperToDto.map(userDetail);
    }

    public UserDetailEntity toEntityForEdit(UserDetailEntity userDetailEntity, UserDetailUpdateDto userDetailUpdateDto) {
        UserDetailEntity userDetailToEdit = toEntity(userDetailEntity);

        modelMapper.map(userDetailUpdateDto, userDetailToEdit);

        return userDetailToEdit;
    }

    public UserDetailEntity toEntity(UserDetailEntity userDetailEntity) {
        return mapperToEntity.map(userDetailEntity);
    }
}
