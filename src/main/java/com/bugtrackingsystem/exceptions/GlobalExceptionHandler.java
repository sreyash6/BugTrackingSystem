package com.bugtrackingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bugtrackingsystem.dto.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    protected ResponseEntity<ErrorDTO> handleResourceAlreadyExistException(ResourceAlreadyExistException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.CONFLICT);
    }
}
