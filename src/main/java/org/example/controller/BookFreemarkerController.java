package org.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.entity.Book;
import org.example.service.BookService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Service;
import spark.template.freemarker.FreeMarkerEngine;

public class BookFreemarkerController implements Controller {

  private final Service service;
  private final BookService bookService;
  private final FreeMarkerEngine freeMarkerEngine;

  public BookFreemarkerController(
      Service service,
      BookService bookService,
      FreeMarkerEngine freeMarkerEngine
  ) {
    this.service = service;
    this.bookService = bookService;
    this.freeMarkerEngine = freeMarkerEngine;
  }

  @Override
  public void initializeEndpoints() {
    getAllBooks();
  }

  private void getAllBooks() {
    service.get(
        "/",
        (Request request, Response response) -> {
          response.type("text/html; charset=utf-8");
          List<Book> books = bookService.findAll();
          List<Map<String, String>> bookMapList =
              books.stream()
                  .map(book -> Map.of("name", book.getName(), "author", book.getAuthor()))
                  .toList();

          Map<String, Object> model = new HashMap<>();
          model.put("books", bookMapList);
          return freeMarkerEngine.render(new ModelAndView(model, "index.ftl"));
        }
    );
  }
}
