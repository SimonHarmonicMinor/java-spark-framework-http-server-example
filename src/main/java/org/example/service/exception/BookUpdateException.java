package org.example.service.exception;

public class BookUpdateException extends RuntimeException {

  public BookUpdateException(String message, Throwable cause) {
    super(message, cause);
  }
}
