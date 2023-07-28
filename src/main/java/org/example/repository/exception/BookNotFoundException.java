package org.example.repository.exception;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String message) {
    super(message);
  }

  public BookNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
