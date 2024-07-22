package com.lag.todoapp.rest.todoapprest.advice;

import com.lag.todoapp.rest.todoapprest.dto.ErrorResponseDto;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.exception.OptionNotFoundException;
import com.lag.todoapp.rest.todoapprest.exception.RoleNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({RoleNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleRoleNotFoundException(RoleNotFoundException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage()).code(HttpStatus.NOT_FOUND.value()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(AccessNotGrantedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessNotGrantedException(AccessNotGrantedException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage()).code(HttpStatus.FORBIDDEN.value()).build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
    }

    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOptionNotFoundException(OptionNotFoundException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage()).code(HttpStatus.NOT_FOUND.value()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
