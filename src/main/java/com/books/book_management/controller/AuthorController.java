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
    public String list(Model model,
                        @RequestParam(value="keyword", required=false) String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int items) {

        Page<Author> authorlistPage;
        
        if (keyword==null || keyword.isEmpty()){
            authorlistPage = authorService.getAllAuthors(PageRequest.of(page, items));
        }
        else{
            authorlistPage = authorService.getAllAuthorsByName(PageRequest.of(page, items), keyword);
        }
        model.addAttribute("authorlistPage", authorlistPage);

        return "authors";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("author", new Author());
        return "author_form";
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
        return "author_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

    @PostMapping("/delete/bulk")
    public String bulkDelete(@RequestParam("authorIds") List<Long> ids) {
        authorService.bulkDeleteAuthors(ids);
        return "redirect:/authors"; 
    }
}