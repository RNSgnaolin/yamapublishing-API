package com.yama.publishing.service.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yama.publishing.domain.author.Author;
import com.yama.publishing.domain.author.DataAuthorUpdate;
import com.yama.publishing.domain.book.Book;
import com.yama.publishing.domain.book.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Service
@NoArgsConstructor
public class AuthorService {

    @Autowired
    public AuthorService(BookRepository repository) {
        this.repository = repository;
    }

    private BookRepository repository;

    public List<Author> searchAll() {
        
        List<Author> authors = new ArrayList<>();

        for (Book b : repository.findAll()) {
            authors.add(b.getAuthor());
        }

        Collections.sort(authors);
        return authors.stream().distinct().collect(Collectors.toList());

    }

    public List<Author> searchByName(String searchPattern) {

            List<Book> books = repository.findAll().stream()
            .filter(b -> b.getAuthor().getName().toLowerCase().contains(searchPattern.toLowerCase()))
            .toList();
            
            List<Author> results = new ArrayList<>();

            for (Book b : books) {
                results.add(b.getAuthor());
            }

            return results;
            
        }       

    @SuppressWarnings("null")
    public Page<Author> toPage(@NonNull List<Author> authors, @NonNull Pageable pageable) {

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), authors.size());
        List<Author> pageContent = authors.subList(start, end);

        return new PageImpl<>(pageContent, pageable, authors.size());
        
    }

    public void update(List<Author> authors, DataAuthorUpdate data) {

        if (authors.isEmpty())
            throw new EntityNotFoundException();

        for (Author a : authors) {
            a.setPhone(data.phone());
            a.setEmail(data.email());
        }

    }
    
}
