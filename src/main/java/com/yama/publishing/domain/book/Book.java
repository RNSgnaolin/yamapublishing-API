package com.yama.publishing.domain.book;

import java.time.LocalDate;

import com.yama.publishing.domain.author.Author;
import com.yama.publishing.domain.genre.Genre;
import com.yama.publishing.service.datetime.DateTimeService;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Book")
@Table(name = "books")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Book {

    public Book(DataBook data) {
        this.title = data.title();
        this.author = new Author(data.author());
        this.releaseDate = DateTimeService.format(data.releaseDate());
        this.pages = data.pages();
        this.price = data.price();
        this.genre = data.genre();
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    private Integer pages;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    
    @Embedded
    private Author author;

    public void setTitle(String title) {
        if (title != null)
            this.title = title;
    }

    public void setPages(Integer pages) {
        if (pages != null)
            this.pages = pages;
    }

    public void setPrice(Integer price) {
        if (price != null)
            this.price = price;
    }

}
