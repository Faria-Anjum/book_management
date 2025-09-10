package com.books.book_management.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.books.book_management.entity.Book;

public interface BookServiceIF {
    Page<Book> getAllBooks(Pageable pageable);
    Book getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(Long id);
    Page<Book> getAllBooksByTitleOrAuthor(Pageable pageable, String keyword);
    void bulkDeleteBooks(List<Long> ids);
}
