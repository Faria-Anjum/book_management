package com.books.book_management.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
// import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @PastOrPresent(message = "Publication date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="author_id")
    private Author author;

    private String imagePath;
}
