package com.books.book_management.service;
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
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
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
}
