package com.yama.publishing.domain.author;

public record DataAuthorListing(String name) {

    public DataAuthorListing(Author author) {
        this(author.getName());
    }
    
}
