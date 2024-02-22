package com.yama.publishing.domain.book;

import com.yama.publishing.domain.genre.Genre;

public record DataBookListing(Long id, String title, String author, Integer pages, Integer price, Genre genre) {

    public DataBookListing(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getPages(), book.getPrice(), book.getGenre());
    }
    
}
