package org.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.example.entity.Book;
import org.example.repository.exception.BookIdDuplicatedException;
import org.example.repository.exception.BookNotFoundException;

public class InMemoryBookRepository implements BookRepository {

  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<Long, Book> booksMap = new ConcurrentHashMap<>();

  @Override
  public long generateId() {
    return nextId.incrementAndGet();
  }

  @Override
  public List<Book> findAll() {
    return new ArrayList<>(booksMap.values());
  }

  @Override
  public Book findById(long bookId) {
    Book book = booksMap.get(bookId);
    if (book == null) {
      throw new BookNotFoundException("Cannot find book by id=" + bookId);
    }
    return book;
  }

  @Override
  public synchronized void create(Book book) {
    if (booksMap.get(book.getId()) != null) {
      throw new BookIdDuplicatedException("Book with the given id already exists: " + book.getId());
    }
    booksMap.put(book.getId(), book);
  }

  @Override
  public synchronized void update(Book book) {
    if (booksMap.get(book.getId()) == null) {
      throw new BookNotFoundException("Cannot find book by id=" + book.getId());
    }
    booksMap.put(book.getId(), book);
  }

  @Override
  public void delete(long bookId) {
    if (booksMap.remove(bookId) == null) {
      throw new BookNotFoundException("Cannot find book by id=" + bookId);
    }
  }
}
