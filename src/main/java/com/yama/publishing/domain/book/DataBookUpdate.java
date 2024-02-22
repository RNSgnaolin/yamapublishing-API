package com.yama.publishing.domain.book;

public record DataBookUpdate(
    String title, 
    Integer pages, 
    Integer price) {

    DataBookUpdate(Book book) {
        this(book.getTitle(), book.getPages(), book.getPrice());
    }
    
}
