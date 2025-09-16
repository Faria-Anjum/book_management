package com.books.book_management.controller;

import java.util.List;
// import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.book_management.entity.Author;
import com.books.book_management.service.AuthorServiceIF;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {
    
    private final AuthorServiceIF authorService;

    public AuthorRestController(AuthorServiceIF authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Page<Author>> list(@RequestParam(value="keyword", required=false) String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int items) {

        Page<Author> authorlistPage;
        
        if (keyword==null || keyword.isEmpty()){
            authorlistPage = authorService.getAllAuthors(PageRequest.of(page, items));
        }
        else{
            authorlistPage = authorService.getAllAuthorsByName(PageRequest.of(page, items), keyword);
        }

        return ResponseEntity.ok(authorlistPage);
    } 

    @GetMapping("/new")
    public ResponseEntity<Author> form() {
        Author author = new Author();
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> save(Author author) {
        Author newAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(newAuthor);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Author> editForm(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        String response = String.format("Author with %d has been deleted.", id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/bulk")
    public ResponseEntity<String> bulkDelete(@RequestParam("authorIds") List<Long> ids) {
        authorService.bulkDeleteAuthors(ids);
        String idList = ids.toString();
        String response = String.format("Book with IDs %s has been deleted.", idList);
        return ResponseEntity.ok(response);
    }

}
