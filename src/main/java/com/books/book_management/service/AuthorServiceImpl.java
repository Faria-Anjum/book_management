package com.books.book_management.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.books.book_management.repository.AuthorRepository;
import com.books.book_management.entity.Author;

@Service
public class AuthorServiceImpl implements AuthorServiceIF{

    private final AuthorRepository authorRepo;

    public AuthorServiceImpl(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public List<Author> getAllAuthors(){
        return authorRepo.findAll();
    };

    @Override
    public Author getAuthorById(Long id){
        return authorRepo.findById(id).orElseThrow();
    }

    @Override
    public Author saveAuthor(Author author){
        return authorRepo.save(author);
    }

    @Override
    public void deleteAuthor(Long id){
        authorRepo.deleteById(id);
    }

    @Override
    public List<Author> getAllAuthorsByName(String keyword){
        return authorRepo.findByNameContaining(keyword);
    }

}
