package com.books.book_management.service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.books.book_management.repository.BookRepository;
import com.books.book_management.entity.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookServiceIF{
    private final BookRepository bookRepo;


    public BookServiceImpl(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    @Override
    public Book getBookById(Long id){
        return bookRepo.findById(id).orElseThrow();
    }

    @Override
    public Book saveBook(Book book){
        return bookRepo.save(book);
    }

    @Override
    public void deleteBook(Long id){
        bookRepo.deleteById(id);
    }

    @Override
    public Page<Book> getAllBooksByTitleOrAuthor(Pageable pageable, String keyword){
        return bookRepo.findByTitleOrAuthorContaining(pageable, keyword);
    }

    @Override
    public void bulkDeleteBooks(List<Long> ids){
        bookRepo.deleteAllById(ids);
    }
}
