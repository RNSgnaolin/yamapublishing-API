package com.yama.publishing.domain.author;

import jakarta.validation.constraints.NotBlank;

public record DataAuthorUpdate(
    @NotBlank
    String name, 
    String phone, 
    String email) {

    DataAuthorUpdate(Author author) {
        this(author.getName(), author.getPhone(), author.getEmail());
    }
    
}