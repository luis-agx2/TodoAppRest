package com.lag.todoapp.rest.todoapprest.service;

import com.lag.todoapp.rest.todoapprest.dto.entrada.LoginEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.RegisterEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.salida.LoginSalidaDto;
import com.lag.todoapp.rest.todoapprest.dto.salida.RegisterSalidaDto;

public interface AuthenticationService {
    LoginSalidaDto authenticate(LoginEntradaDto userLogin) throws Exception;
    RegisterSalidaDto registerUser(RegisterEntradaDto userRegister) throws Exception;
}
