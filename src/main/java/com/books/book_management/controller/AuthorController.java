package com.books.book_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    

    @GetMapping
    public String authorList() {
        return "authors";
    }

    @GetMapping("/new")
    public String newAuthor() {
        return "author_form";
    }
}