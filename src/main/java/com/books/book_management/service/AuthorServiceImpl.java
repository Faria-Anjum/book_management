package com.books.book_management.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.books.book_management.repository.AuthorRepository;
import com.books.book_management.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

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
    public Page<Author> getAllAuthors(Pageable pageable){
        return authorRepo.findAll(pageable);
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
    public Page<Author> getAllAuthorsByName(Pageable pageable, String keyword){
        return authorRepo.findByNameContaining(pageable, keyword);
    }

    @Override
    public void bulkDeleteAuthors(List<Long> ids){
        authorRepo.deleteAllById(ids);
    }
}
