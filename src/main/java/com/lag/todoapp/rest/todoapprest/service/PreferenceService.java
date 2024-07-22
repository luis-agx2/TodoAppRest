package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.PreferenceDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.PreferenceUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;

public interface PreferenceService {
    PreferenceDto getMe();

    PreferenceDto updateMe(PreferenceUpdateDto preferenceUpdateDto) throws OptionNotFoundException;
}
