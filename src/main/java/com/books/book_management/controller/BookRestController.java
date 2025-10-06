package com.books.book_management.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public Map<String, Object> bookTable(HttpServletRequest request){

        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String search = request.getParameter("search[value]");
        PageRequest pageRequest;

        int page = start / length;

        if (request.getParameter("order[0][dir]")!=null){
            String orderDir = request.getParameter("order[0][dir]");
            String sortColumn = request.getParameter("columns[" + request.getParameter("order[0][column]") + "][data]");
            
            Sort.Direction direction;

            if ("desc".equalsIgnoreCase(orderDir)) {
                direction = Sort.Direction.DESC;
            } else {
                direction = Sort.Direction.ASC;
            }
            //pagerequest is a spring data class, contains page, length and sort data
            //sort is another class, contains sort direction, order, nyllhandling and typed sort
            pageRequest = PageRequest.of(page, length, Sort.by(direction, sortColumn));
        }
        else{
            pageRequest = PageRequest.of(page, length);
        }
        
        //creating a page object with book
        Page<Book> bookTablePage;

        //if datatable searchbar is empty
        if (search.isEmpty()){
            bookTablePage = bookService.getAllBooks(pageRequest);
        }
        else{
            bookTablePage = bookService.getAllBooksByTitleOrAuthor(pageRequest, search);
        }

        //creating a dictionary to send data back to datatable
        Map<String, Object> response = new HashMap<>();
        response.put("data", bookTablePage.getContent());
        response.put("recordsTotal", bookTablePage.getTotalElements());
        response.put("recordsFiltered", bookTablePage.getTotalElements());

        return response;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveBook(@RequestPart("book") Book book,
                                    @RequestPart(value="bookImage", required=false) MultipartFile bookImage,
                                    @RequestParam("authorId") Long authorId) throws IOException{

        if (!bookImage.isEmpty()) {  //if an image file has been uploaded
            String uploadDir = "src/main/resources/static/images/";
            UUID uuid = (UUID.randomUUID());
            String fileName = uuid.toString()+bookImage.getOriginalFilename();
            System.out.println(fileName);

            Path path = Paths.get(uploadDir + fileName);
            Files.copy(bookImage.getInputStream(), path);

            book.setImagePath(fileName);
        }
        else{
            if(book.getId()!=null){ //if edit and an image exists
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

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> viewBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete/bulk")
    public ResponseEntity<String> bulkDeleteBooks(@RequestBody List<Long> ids){
        bookService.bulkDeleteBooks(ids);
        return ResponseEntity.noContent().build();
    }
}