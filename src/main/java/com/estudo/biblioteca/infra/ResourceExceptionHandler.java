package com.estudo.biblioteca.infra;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.estudo.biblioteca.dtos.ResponseExceptionErrorDTO;
import com.estudo.biblioteca.infra.exceptions.EntityNotFoundException;
import com.estudo.biblioteca.infra.exceptions.ForeignKeyReferenceException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseExceptionErrorDTO> entityNotFound(EntityNotFoundException e,
      HttpServletRequest request) {

    ResponseExceptionErrorDTO err = new ResponseExceptionErrorDTO();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setError("Resource Not Found");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseExceptionErrorDTO> illegalArgument(IllegalArgumentException e,
      HttpServletRequest request) {

    ResponseExceptionErrorDTO err = new ResponseExceptionErrorDTO();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.BAD_REQUEST.value());
    err.setError("Bad Request");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(ForeignKeyReferenceException.class)
  public ResponseEntity<ResponseExceptionErrorDTO> ForeignKeyReference(ForeignKeyReferenceException e,
      HttpServletRequest request) {

    ResponseExceptionErrorDTO err = new ResponseExceptionErrorDTO();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.CONFLICT.value());
    err.setError("Conflict");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
  }

}
