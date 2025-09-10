package com.books.book_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.books.book_management.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    @Query
    ("select b from Book b where lower(b.title) like lower(concat('%',:keyword,'%'))" +
                            " or lower(b.author.name) like lower(concat('%',:keyword,'%'))")
    List<Book> findByTitleOrAuthorContaining(String keyword);

    // @Query
    // ("select b from Book b where lower(b.title) like lower(concat('%')+ :keyword +('%'))")
    // List<Book> findAllByTitle(@Param("keyword") String keyword);

    // List<Book> findByTitleContaining(String keyword);
}
