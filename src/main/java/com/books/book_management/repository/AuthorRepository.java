package com.books.book_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.book_management.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
}
