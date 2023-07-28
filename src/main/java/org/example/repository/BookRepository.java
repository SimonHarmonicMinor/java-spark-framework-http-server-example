package org.example.repository;

import java.util.List;
import org.example.entity.Book;
import org.example.repository.exception.BookIdDuplicatedException;
import org.example.repository.exception.BookNotFoundException;

public interface BookRepository {

  long generateId();

  List<Book> findAll();

  /**
   * @throws BookNotFoundException
   */
  Book findById(long bookId);

  /**
   * @throws BookIdDuplicatedException
   */
  void create(Book book);

  /**
   * @throws BookNotFoundException
   */
  void update(Book book);

  /**
   * @throws BookNotFoundException
   */
  void delete(long bookId);
}
