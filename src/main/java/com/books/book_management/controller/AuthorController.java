package com.books.book_management.controller;

// import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.books.book_management.service.AuthorServiceIF;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorServiceIF authorService;

    public AuthorController(AuthorServiceIF authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public String authorList() {
        return "authors";
    }

    @GetMapping("/new")
    public String newAuthor() {
        return "author_form";
    }

    // @GetMapping("/edit/{id}")
    // public String editAuthor(@PathVariable Long id, Model model) {
    //     Author author = authorService.getAuthorById(id);
    //     model.addAttribute("author", author);
    //     return "author_form";
    // }
}