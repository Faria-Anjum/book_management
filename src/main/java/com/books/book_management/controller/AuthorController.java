package com.books.book_management.controller;

// import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.books.book_management.entity.Author;
import com.books.book_management.service.AuthorServiceIF;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorServiceIF authorService;

    public AuthorController(AuthorServiceIF authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("author", new Author());
        return "author-form";
    }

    @PostMapping
    public String save(@ModelAttribute Author author) {
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}