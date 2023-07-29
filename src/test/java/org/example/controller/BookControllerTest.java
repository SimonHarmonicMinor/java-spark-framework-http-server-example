package org.example.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import org.example.Application;
import org.example.controller.response.BookCreateResponse;
import org.example.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spark.Service;

class BookControllerTest {

  private Service service;

  @BeforeEach
  void befofeEach() {
    service = Service.ignite();
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void should201IfBookIsSuccessfullyCreated() throws Exception {
    BookService bookService = Mockito.mock(BookService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new BookController(
                service,
                bookService,
                objectMapper
            )
        )
    );
    Mockito.when(bookService.create("book", "jack"))
        .thenReturn(45L);
    application.start();
    service.awaitInitialization();

    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    BodyPublishers.ofString(
                        """
                            { "name": "book", "author": "jack" }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/books".formatted(service.port())))
                .build(),
            BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, response.statusCode());
    BookCreateResponse bookCreateResponse =
        objectMapper.readValue(response.body(), BookCreateResponse.class);
    assertEquals(45L, bookCreateResponse.id());
  }
}