package org.example.service.exception;

public class BookDeleteException extends RuntimeException {

  public BookDeleteException(String message, Throwable cause) {
    super(message, cause);
  }
}
