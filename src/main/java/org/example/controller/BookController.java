package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.request.BookCreateRequest;
import org.example.controller.response.BookCreateResponse;
import org.example.controller.response.ErrorResponse;
import org.example.service.BookService;
import org.example.service.exception.BookCreateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

public class BookController {

  private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

  private final Service service;
  private final BookService bookService;
  private final ObjectMapper objectMapper;

  public BookController(Service service, BookService bookService, ObjectMapper objectMapper) {
    this.service = service;
    this.bookService = bookService;
    this.objectMapper = objectMapper;
  }

  public void start() {
    createBook();
  }

  private void createBook() {
    service.post(
        "/api/books",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          BookCreateRequest bookCreateRequest = objectMapper.readValue(body,
              BookCreateRequest.class);
          try {
            long bookId = bookService.create(bookCreateRequest.name(), bookCreateRequest.author());
            response.status(201);
            return objectMapper.writeValueAsString(new BookCreateResponse(bookId));
          } catch (BookCreateException e) {
            LOG.warn("Cannot create book", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }
}
