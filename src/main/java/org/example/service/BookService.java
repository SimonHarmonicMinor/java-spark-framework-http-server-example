package org.example.service;

import java.util.List;
import org.example.entity.Book;
import org.example.repository.BookRepository;
import org.example.repository.exception.BookIdDuplicatedException;
import org.example.repository.exception.BookNotFoundException;
import org.example.service.exception.BookCreateException;
import org.example.service.exception.BookDeleteException;
import org.example.service.exception.BookFindException;
import org.example.service.exception.BookUpdateException;

public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Book findById(long bookId) {
    try {
      return bookRepository.findById(bookId);
    } catch (BookNotFoundException e) {
      throw new BookFindException("Cannot find book by id=" + bookId, e);
    }
  }

  public long create(String name, String author) {
    long bookId = bookRepository.generateId();
    Book book = new Book(bookId, name, author);
    try {
      bookRepository.create(book);
    } catch (BookIdDuplicatedException e) {
      throw new BookCreateException("Cannot create book", e);
    }
    return bookId;
  }

  public void update(long bookId, String name, String author) {
    Book book;
    try {
      book = bookRepository.findById(bookId);
    } catch (BookNotFoundException e) {
      throw new BookUpdateException("Cannot find book with id=" + bookId, e);
    }

    try {
      bookRepository.update(
          book.withName(name)
              .withAuthor(author)
      );
    } catch (BookNotFoundException e) {
      throw new BookUpdateException("Cannot update book with id=" + bookId, e);
    }
  }

  public void delete(long bookId) {
    try {
      bookRepository.delete(bookId);
    } catch (BookNotFoundException e) {
      throw new BookDeleteException("Cannot delete book with id=" + bookId, e);
    }
  }
}
