package org.example.entity;

import java.util.Objects;

public class Book {
  private final long id;
  private final String name;
  private final String author;

  public Book(long id, String name, String author) {
    this.id = id;
    this.name = name;
    this.author = author;
  }

  public Book withName(String newName) {
    return new Book(this.id, newName, this.author);
  }

  public Book withAuthor(String newAuthor) {
    return new Book(this.id, this.author, newAuthor);
  }


  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAuthor() {
    return author;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", author='" + author + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return id == book.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
