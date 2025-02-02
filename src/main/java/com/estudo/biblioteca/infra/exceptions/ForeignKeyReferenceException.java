package com.estudo.biblioteca.infra.exceptions;

public class ForeignKeyReferenceException extends RuntimeException {
  public ForeignKeyReferenceException(String msg) {
    super(msg);
  }
}
