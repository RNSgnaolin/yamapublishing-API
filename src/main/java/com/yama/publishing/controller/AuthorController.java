package com.yama.publishing.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yama.publishing.domain.author.Author;
import com.yama.publishing.domain.author.DataAuthorListing;
import com.yama.publishing.domain.author.DataAuthorUpdate;
import com.yama.publishing.service.models.AuthorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Page<DataAuthorListing>> getAuthors(Pageable pageable, @RequestParam(value = "search") Optional<String> searchPattern) {

        return ResponseEntity.ok(
            authorService.toPage(authorService.searchAll(), pageable)
            .map(DataAuthorListing::new)
        );
       
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<List<DataAuthorListing>> updateAuthor(@RequestBody @Valid DataAuthorUpdate data) {


        List<Author> authors = authorService.searchByName(data.name());

        authorService.update(authors, data);
        return ResponseEntity.ok(authors.stream().map(DataAuthorListing::new).distinct().toList());

    }
}
