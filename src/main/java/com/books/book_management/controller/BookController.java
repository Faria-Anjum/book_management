package com.books.book_management.controller;

import java.time.LocalDate;

import javax.validation.Valid;

// import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("today", LocalDate.now());
        return "book-form";
    }

    // postmapping
    // public String save(@RequestParam("title") String title, @RequestParam("publicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publicationDate, @RequestParam("authorId") Long authorId){
    //     Author author = authorRepo.findById(authorId).orElseThrow();
    //     Book book = new Book();
    //     book.setTitle(title);
    //     book.setPublicationDate(publicationDate);
    
    @PostMapping
    public String save(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model, @RequestParam("authorId") Long authorId) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorRepo.findAll());
            return "book-form";
        }

        // Author author = authorRepo.findById(book.getAuthor().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid author Id"));
        Author author = authorRepo.findById(authorId).orElseThrow();
        book.setAuthor(author);

        bookRepo.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("authors", authorRepo.findAll());
        model.addAttribute("today", LocalDate.now());
        return "book-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookRepo.deleteById(id);
        return "redirect:/books";
    }
}