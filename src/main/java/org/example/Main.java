package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.controller.BookController;
import org.example.repository.InMemoryBookRepository;
import org.example.service.BookService;
import spark.Service;

public class Main {

  public static void main(String[] args) {
    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new BookController(
                service,
                new BookService(
                    new InMemoryBookRepository()
                ),
                objectMapper
            )
        )
    );
    application.start();
  }
}
