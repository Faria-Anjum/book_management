package com.books.book_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.books.book_management.entity.Author;
// import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorRepository extends JpaRepository<Author, Long>{
    Page<Author> findByNameContaining(Pageable pageable, String keyword);
}
