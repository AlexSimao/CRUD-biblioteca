package com.estudo.biblioteca.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.estudo.biblioteca.dtos.ResponseStandardErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseStandardErrorDTO> entityNotFound(EntityNotFoundException e,
      HttpServletRequest request) {
    ResponseStandardErrorDTO err = new ResponseStandardErrorDTO();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setError("Resource Not Found");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

}
