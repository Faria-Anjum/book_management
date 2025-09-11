package com.books.book_management.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.books.book_management.entity.Author;
import org.springframework.data.domain.Page;

public interface AuthorServiceIF {
    List<Author> getAllAuthors();
    Page<Author> getAllAuthors(Pageable pageable);
    Author getAuthorById(Long id);
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);
    Page<Author> getAllAuthorsByName(Pageable pageable, String keyword);
    void bulkDeleteAuthors(List<Long> ids);
}
