package com.books.book_management.controller;

import java.util.HashMap;
import java.util.List;
// import java.util.Map;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // @GetMapping
    // public ResponseEntity<Page<Author>> list(@RequestParam(value="keyword", required=false) String keyword,
    //                     @RequestParam(defaultValue = "0") int page,
    //                     @RequestParam(defaultValue = "5") int items) {

    //     Page<Author> authorlistPage;
        
    //     if (keyword==null || keyword.isEmpty()){
    //         authorlistPage = authorService.getAllAuthors(PageRequest.of(page, items));
    //     }
    //     else{
    //         authorlistPage = authorService.getAllAuthorsByName(PageRequest.of(page, items), keyword);
    //     }

    //     return ResponseEntity.ok(authorlistPage);
    // }

    // @GetMapping
    // public Map<String, Object> authorTable(@RequestParam(name = "start", defaultValue = "0") int start,
    //                                         @RequestParam(name = "length", defaultValue = "10") int length,
    //                                         @RequestParam(name = "search[value]", defaultValue = "") String search,
    //                                         @RequestParam(name = "order[0][column]", required = false) Integer orderColumn,
    //                                         @RequestParam(name = "order[0][dir]", required = false) String orderDir,
    //                                         @RequestParam Map<String, String> requestParams){

    //     int page = start / length;

    //     String sortColumn = "id";
    //     if (orderColumn != null) {
    //         sortColumn = requestParams.get("columns[" + orderColumn + "][data]");
    //     }

    //     Sort.Direction direction;
    //     if ("desc".equalsIgnoreCase(orderDir)) {
    //         direction = Sort.Direction.DESC;
    //     } else {
    //         direction = Sort.Direction.ASC;
    //     }
    //     // = "desc".equalsIgnoreCase(orderDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
    //     PageRequest pageRequest = PageRequest.of(page, length, Sort.by(direction, sortColumn));

    //     Page<Author> authorTablePage;

    //     if (search.isEmpty()){
    //         authorTablePage = authorService.getAllAuthors(pageRequest);
    //     }
    //     else{
    //         authorTablePage = authorService.getAllAuthorsByName(pageRequest, search);
    //     }

    //     Map<String, Object> response = new HashMap<>();
    //     response.put("data", authorTablePage.getContent());
    //     response.put("recordsTotal", authorTablePage.getTotalElements());
    //     response.put("recordsFiltered", authorTablePage.getTotalElements());

    //     return response;
    // }

    @GetMapping
    public Map<String, Object> authorTable(HttpServletRequest request){

        System.out.println(request);
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
        
        //creating a page object with author
        Page<Author> authorTablePage;

        //if datatable searchbar is empty
        if (search.isEmpty()){
            authorTablePage = authorService.getAllAuthors(pageRequest);
        }
        else{
            authorTablePage = authorService.getAllAuthorsByName(pageRequest, search);
        }

        //creating a dictionary to send data back to datatable
        Map<String, Object> response = new HashMap<>();
        response.put("data", authorTablePage.getContent());
        response.put("recordsTotal", authorTablePage.getTotalElements());
        response.put("recordsFiltered", authorTablePage.getTotalElements());

        return response;
    }

    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(newAuthor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> viewAuthor(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> bulkDelete(@RequestBody List<Long> ids) { //grabbing the playload sent by ajax
        authorService.bulkDeleteAuthors(ids);
        return ResponseEntity.noContent().build();
    }
}
