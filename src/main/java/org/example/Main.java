package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.controller.BookController;
import org.example.controller.BookFreemarkerController;
import org.example.repository.InMemoryBookRepository;
import org.example.service.BookService;
import org.example.template.TemplateFactory;
import spark.Service;

public class Main {

  public static void main(String[] args) {
    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    final var bookService = new BookService(
        new InMemoryBookRepository()
    );
    Application application = new Application(
        List.of(
            new BookController(
                service,
                bookService,
                objectMapper
            ),
            new BookFreemarkerController(
                service,
                bookService,
                TemplateFactory.freeMarkerEngine()
            )
        )
    );
    application.start();
  }
}
