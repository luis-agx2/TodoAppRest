package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.entrada.LoginEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.RegisterEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.salida.LoginSalidaDto;
import com.lag.todoapp.rest.todoapprest.dto.salida.RegisterSalidaDto;
import com.lag.todoapp.rest.todoapprest.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping()
    public ResponseEntity<LoginSalidaDto> login(@Valid @RequestBody LoginEntradaDto userLogin) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(userLogin));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterSalidaDto> register(@Valid @RequestBody RegisterEntradaDto userRegister) throws Exception {
        return ResponseEntity.ok(authenticationService.registerUser(userRegister));
    }
}
