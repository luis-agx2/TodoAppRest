package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.UserDetailDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.UserDetailUpdateDto;

public interface UserDetailService {
    UserDetailDto getMe();

    UserDetailDto updateMe(UserDetailUpdateDto userDetailUpdateDto);
}
