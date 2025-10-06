package com.books.book_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public String list() {
        return "books";
    }

    @GetMapping("/new")
    public String form() {
        return "book_form";
    }
}

