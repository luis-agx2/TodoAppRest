package com.lag.todoapp.rest.todoapprest.mapper;

import com.lag.todoapp.rest.todoapprest.dto.PreferenceDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.PreferenceUpdateDto;
import com.lag.todoapp.rest.todoapprest.entity.PreferenceEntity;
import com.lag.todoapp.rest.todoapprest.enums.TaskStatusEnum;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class PreferenceMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    TypeMap<PreferenceEntity, PreferenceDto> mapperPreferences = modelMapper.createTypeMap(PreferenceEntity.class, PreferenceDto.class);
    TypeMap<PreferenceUpdateDto, PreferenceEntity> mapperUpdate = modelMapper.createTypeMap(PreferenceUpdateDto.class, PreferenceEntity.class);
    TypeMap<PreferenceEntity, PreferenceEntity> mapperEntityToEntity = modelMapper.createTypeMap(PreferenceEntity.class, PreferenceEntity.class);

    public PreferenceMapper () {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public PreferenceDto toDto(PreferenceEntity preferenceEntity) {
        return mapperPreferences.map(preferenceEntity);
    }

    public PreferenceEntity toEntityForUpdate(PreferenceEntity preferenceEntity, PreferenceUpdateDto preferenceUpdateDto) throws OptionNotFoundException {
        PreferenceEntity preferenceToEdit = toEntity(preferenceEntity);

        try {
            modelMapper.addMappings(new PropertyMap<>() {
                @Override
                protected void configure() {
                    // Define mapping for theme
                    when(ctx -> ctx.getSource() != null)
                            .using(ctx -> TaskStatusEnum.fromString(ctx.getSource().toString()));
                }
            });

            mapperUpdate.map(preferenceUpdateDto, preferenceToEdit);
        } catch (Exception e) {
            throw new OptionNotFoundException("Not found some option of your preferences");
        }

        return preferenceToEdit;
    }

    public PreferenceEntity toEntity(PreferenceEntity preferenceEntity) {
        return mapperEntityToEntity.map(preferenceEntity);
    }
}
