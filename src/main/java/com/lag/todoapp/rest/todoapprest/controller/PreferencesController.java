package com.lag.todoapp.rest.todoapprest.controller;

import com.lag.todoapp.rest.todoapprest.dto.PreferenceDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.PreferenceUpdateDto;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import com.lag.todoapp.rest.todoapprest.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferencesController {
    private final PreferenceService preferenceService;

    @Autowired
    public PreferencesController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/me")
    public ResponseEntity<PreferenceDto> getMe() {
        return ResponseEntity.ok(preferenceService.getMe());
    }

    @PutMapping("/me")
    public ResponseEntity<PreferenceDto> updateMe(@RequestBody PreferenceUpdateDto preferenceUpdateDto) throws OptionNotFoundException {
        return ResponseEntity.ok(preferenceService.updateMe(preferenceUpdateDto));
    }
}
