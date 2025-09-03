package com.books.book_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.books.book_management.entity.Author;
import com.books.book_management.entity.Book;
import com.books.book_management.repository.AuthorRepository;
import com.books.book_management.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public BookController(BookRepository bookRepo, AuthorRepository authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        return "books";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorRepo.findAll());
        return "book-form";
    }

    @PostMapping
    public String save(@RequestParam("title") String title, @RequestParam("authorId") Long authorId){
        Author author = authorRepo.findById(authorId).orElseThrow();
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);

        bookRepo.save(book);
        return "redirect:/books";
    }
}