package com.books.book_management.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
// import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.books.book_management.entity.Author;
import com.books.book_management.entity.Book;
import com.books.book_management.service.AuthorServiceIF;
import com.books.book_management.service.BookServiceIF;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookServiceIF bookService;
    private final AuthorServiceIF authorService;

    public BookRestController(BookServiceIF bookService, AuthorServiceIF authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }
    
    // @GetMapping
    // public ResponseEntity<Page<Book>> list(
    //                 @RequestParam(value="keyword", required = false) String keyword,
    //                 @RequestParam(defaultValue = "0") int page,
    //                 @RequestParam(defaultValue = "5") int items) {

    //     Page<Book> booklistPage;

    //     if (keyword==null || keyword.isEmpty()){
    //         booklistPage = bookService.getAllBooks(PageRequest.of(page, items));
    //     }
    //     else{
    //         booklistPage = bookService.getAllBooksByTitleOrAuthor(PageRequest.of(page, items), keyword);
    //     }
    //     return ResponseEntity.ok(booklistPage);
    // }

    // @GetMapping("/new")
    // public ResponseEntity<Map<String, Object>> bookForm(){
    //     // booklist = bookService.getBookById(book.id);
    //     Map<String, Object> response = new HashMap<>();
    //     response.put("book", new Book());
    //     response.put("authors", authorService.getAllAuthors());
    //     response.put("today", LocalDate.now());

    //     return ResponseEntity.ok(response);
    // }

    @GetMapping
    public Map<String, Object> bookTable(HttpServletRequest request){

        

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> save(@RequestPart("book") Book book,
                                    @RequestPart(value="bookImage", required=false) MultipartFile bookImage,
                                    @RequestParam("authorId") Long authorId) throws IOException{

        if (!bookImage.isEmpty()) {
            String uploadDir = "src/main/resources/static/images/";
            String fileName = bookImage.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(bookImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Save only the filename in DB
            book.setImagePath(fileName);
        }
        else{
            if(book.getId()!=null){
                Book current = bookService.getBookById(book.getId());
                if (current != null) {
                    book.setImagePath(current.getImagePath());
                }
            }
        }

        if (book.getPublicationDate().isAfter(LocalDate.now())){
            return ResponseEntity.badRequest().body("Publication date cannot be in the future");
        }

        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);

        bookService.saveBook(book);

        return ResponseEntity.ok("New book created.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> viewBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("book", book);
        response.put("authors", authorService.getAllAuthors());
        response.put("today", LocalDate.now());
        return ResponseEntity.ok(response);
    }

    // @PutMapping("/edit/{id}")
    // public ResponseEntity<String> editBook(@PathVariable Long id) {
    //     Book book = bookService.getBookById(id);
    //     book.setId(id);
    //     bookService.saveBook(book);
    //     String response = String.format("Book with ID %d has been updated.", id);
    //     return ResponseEntity.ok(response);
    // }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        String response = String.format("Book with ID %d has been deleted.",id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/bulk")
    public ResponseEntity<String> bulkDelete(@RequestBody List<Long> ids){
        bookService.bulkDeleteBooks(ids);
        String idList = ids.toString();
        String response = String.format("Book with IDs %s has been deleted.",idList);
        return ResponseEntity.ok(response);
    }
}
