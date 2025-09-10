package com.books.book_management.service;

import java.util.List;
import com.books.book_management.entity.Author;

public interface AuthorServiceIF {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);
    List<Author> getAllAuthorsByName(String keyword);
    void bulkDeleteAuthors(List<Long> ids);
}
