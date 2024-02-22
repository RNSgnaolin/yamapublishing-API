package com.yama.publishing.domain.author;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataAuthor(
    @NotBlank
    String name, 
    String phone,
    @Email 
    String email) {   
}