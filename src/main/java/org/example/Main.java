package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.BookController;
import org.example.repository.InMemoryBookRepository;
import org.example.service.BookService;
import spark.Service;

public class Main {

  public static void main(String[] args) {
    BookController bookController = new BookController(
        Service.ignite(),
        new BookService(
            new InMemoryBookRepository()
        ),
        new ObjectMapper()
    );
    bookController.start();
  }
}
