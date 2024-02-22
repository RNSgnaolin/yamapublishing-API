package com.yama.publishing.domain.book;

import org.springframework.format.annotation.DateTimeFormat;

import com.yama.publishing.domain.author.DataAuthor;
import com.yama.publishing.domain.genre.Genre;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataBook(
    @NotBlank
    String title,
    @NotBlank
    @DateTimeFormat(pattern = "MM/dd/yyyy") 
    String releaseDate, 
    @NotNull
    Integer pages,
    @NotNull
    Integer price,
    @NotNull
    Genre genre,
    @NotNull @Valid
    DataAuthor author) {
}