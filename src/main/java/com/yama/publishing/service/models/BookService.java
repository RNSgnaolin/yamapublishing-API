package com.yama.publishing.service.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yama.publishing.domain.book.Book;
import com.yama.publishing.domain.book.BookRepository;
import com.yama.publishing.domain.book.DataBookUpdate;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.NonNull;

@Service
@Getter
public class BookService { 

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    private BookRepository repository;

    public List<Book> searchAll() {

        return repository.findAll();

    }

    public Book searchById(@NonNull Long id) {

        return repository.findById(id).orElseThrow(EntityNotFoundException::new);

    }

    public List<Book> searchByQuery(String searchPattern) {

        List<Book> books = repository.findAll();
        List<Book> results = new ArrayList<>();
        searchPattern = searchPattern.toLowerCase();

        for (Book b : books) {

            if (
                b.getTitle().toLowerCase().contains(searchPattern) ||
                b.getAuthor().getName().toLowerCase().contains(searchPattern)
            ) {
                results.add(b);
            }

        }

        return results;
    }

    @SuppressWarnings("null")
    public Page<Book> toPage(@NonNull List<Book> books, @NonNull Pageable pageable) {

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), books.size());
        List<Book> pageContent = books.subList(start, end);

        return new PageImpl<>(pageContent, pageable, books.size());

    }

    public void update(Book book, DataBookUpdate data) {
        book.setTitle(data.title());
        book.setPages(data.pages());
        book.setPrice(data.price());
    }
    
}