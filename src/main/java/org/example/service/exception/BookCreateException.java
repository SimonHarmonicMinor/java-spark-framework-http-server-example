package org.example.service.exception;

public class BookCreateException extends RuntimeException {

  public BookCreateException(String message) {
    super(message);
  }

  public BookCreateException(String message, Throwable cause) {
    super(message, cause);
  }
}
