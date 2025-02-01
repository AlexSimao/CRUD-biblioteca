package com.estudo.biblioteca.infra.exceptions;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String msg) {
    super(msg);
  }

}
