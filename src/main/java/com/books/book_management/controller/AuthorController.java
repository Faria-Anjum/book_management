package com.books.book_management.controller;

// import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.books.book_management.entity.Author;
import com.books.book_management.service.AuthorServiceIF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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

    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author_form";
    }
}