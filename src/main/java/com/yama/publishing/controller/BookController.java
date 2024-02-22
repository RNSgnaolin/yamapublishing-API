package com.yama.publishing.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.yama.publishing.domain.book.Book;
import com.yama.publishing.domain.book.DataBook;
import com.yama.publishing.domain.book.DataBookListing;
import com.yama.publishing.domain.book.DataBookUpdate;
import com.yama.publishing.service.models.BookService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;

@RestController
@RequestMapping("/books")
public class BookController {


    private BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<DataBookListing>> getBookByQuery(Pageable pageable, @RequestParam(value = "search") Optional<String> searchPattern) {

        if (!searchPattern.isPresent()) {
            return ResponseEntity.ok(
                bookService.toPage(bookService.searchAll(), pageable)
                .map(DataBookListing::new)
            );
        }

        return ResponseEntity.ok(
            bookService.toPage(bookService.searchByQuery(searchPattern.get()), pageable)
            .map(DataBookListing::new)
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<DataBookListing> getBookById(@PathVariable Long id) {
        
        return ResponseEntity.ok(new DataBookListing(bookService.searchById(id)));

    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<DataBookListing> register(@RequestBody @Valid DataBook data, UriComponentsBuilder uriBuilder) {

        Book book = new Book(data);
        bookService.getRepository().save(book);

        var uri = uriBuilder.path("/books/add/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataBookListing(book));

    }    

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DataBookListing> updateBook(@RequestBody @Valid DataBookUpdate data, @PathVariable @NonNull Long id) {

        Book book = bookService.searchById(id);

        bookService.update(book, data);
        return ResponseEntity.ok(new DataBookListing(book));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Book> deleteBook(@PathVariable @NonNull Long id) {

        bookService.getRepository().deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}