package com.books.book_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.books.book_management.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
}
