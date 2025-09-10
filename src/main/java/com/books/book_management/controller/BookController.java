package com.books.book_management.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

// import javax.validation.Valid;

// import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.books.book_management.entity.Author;
import com.books.book_management.entity.Book;
// import com.books.book_management.repository.AuthorRepository;
// import com.books.book_management.repository.BookRepository;
import com.books.book_management.service.BookServiceIF;
import com.books.book_management.service.AuthorServiceIF;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookServiceIF bookService;
    private final AuthorServiceIF authorService;

    public BookController(BookServiceIF bookService, AuthorServiceIF authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model, @RequestParam(value="keyword", required = false) String keyword) {
        if (keyword==null || keyword.isEmpty()){
            model.addAttribute("books", bookService.getAllBooks());
        }
        else{
            model.addAttribute("books", bookService.getAllBooksByTitleOrAuthor(keyword));
        }
        return "books";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("today", LocalDate.now());
        return "book_form";
    }

    // postmapping
    // public String save(@RequestParam("title") String title, @RequestParam("publicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publicationDate, @RequestParam("authorId") Long authorId){
    //     Author author = authorRepo.findById(authorId).orElseThrow();
    //     Book book = new Book();
    //     book.setTitle(title);
    //     book.setPublicationDate(publicationDate);
    
    @PostMapping
    public String save(@ModelAttribute("book") Book book, Model model,
                        @RequestParam("bookImage") MultipartFile bookImage,
                        @RequestParam("authorId") Long authorId) throws IOException{
        
        if (!bookImage.isEmpty()) {
            String uploadDir = "src/main/resources/static/images/";

            String fileName = bookImage.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);
            Files.copy(bookImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Save only the filename in DB
            book.setImagePath(fileName);
    }

        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);

        bookService.saveBook(book);
        return "redirect:/books";

        // if (result.hasErrors()) {
        //     model.addAttribute("authors", authorRepo.findAll());
        //     return "book-form";
        // }

        // Author author = authorRepo.findById(book.getAuthor().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid author Id"));
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("today", LocalDate.now());
        return "book_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}