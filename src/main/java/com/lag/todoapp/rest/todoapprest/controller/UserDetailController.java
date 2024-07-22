package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.UserDetailDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.UserDetailUpdateDto;
import com.lag.todoapp.rest.todoapprest.service.UserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/details")
public class UserDetailController {
    private final UserDetailService userDetailService;

    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailDto> getMe() {
        return ResponseEntity.ok(userDetailService.getMe());
    }

    @PutMapping("/me")
    public ResponseEntity<UserDetailDto> updateMe(@RequestBody UserDetailUpdateDto userDetailUpdateDto) {
        return ResponseEntity.ok(userDetailService.updateMe(userDetailUpdateDto));
    }
}
