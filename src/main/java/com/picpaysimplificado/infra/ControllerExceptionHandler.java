package com.picpaysimplificado.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity handleDuplicateEntity(DataIntegrityViolationException e) {
    return ResponseEntity.badRequest().body("Duplicate entity");
  }
  
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity handle404(DataIntegrityViolationException e) {
    return ResponseEntity.notFound().build();
  }
}
