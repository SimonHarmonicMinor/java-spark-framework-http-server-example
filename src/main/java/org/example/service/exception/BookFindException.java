package org.example.service.exception;

public class BookFindException extends RuntimeException {

  public BookFindException(String message, Throwable cause) {
    super(message, cause);
  }
}
