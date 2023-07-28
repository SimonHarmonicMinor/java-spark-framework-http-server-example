package org.example.repository.exception;

public class BookIdDuplicatedException extends RuntimeException {

  public BookIdDuplicatedException(String message) {
    super(message);
  }

  public BookIdDuplicatedException(String message, Throwable cause) {
    super(message, cause);
  }
}
