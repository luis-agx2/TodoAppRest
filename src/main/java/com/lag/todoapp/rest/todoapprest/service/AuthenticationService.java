package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.entrada.LoginEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.RegisterEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.LoginDto;
import com.lag.todoapp.rest.todoapprest.dto.RegisterDto;

public interface AuthenticationService {
    LoginDto authenticate(LoginEntradaDto userLogin) throws Exception;
    RegisterDto registerUser(RegisterEntradaDto userRegister) throws Exception;
}
