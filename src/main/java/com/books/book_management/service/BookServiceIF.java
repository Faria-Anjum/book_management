package com.books.book_management.service;

import java.util.List;
import com.books.book_management.entity.Book;

public interface BookServiceIF {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(Long id);
}
