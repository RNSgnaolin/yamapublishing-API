package com.yama.publishing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yama.publishing.domain.author.Author;
import com.yama.publishing.domain.author.DataAuthor;
import com.yama.publishing.domain.book.Book;
import com.yama.publishing.domain.book.BookRepository;
import com.yama.publishing.domain.book.DataBook;
import com.yama.publishing.domain.book.DataBookListing;
import com.yama.publishing.domain.book.DataBookUpdate;
import com.yama.publishing.domain.genre.Genre;
import com.yama.publishing.service.models.BookService;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@SpringBootTest
@ActiveProfiles("test")
@WithMockUser(username = "user", roles = "USER")
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DataBook> bookDataJson;

    @Autowired
    private JacksonTester<DataBookListing> bookListingJson;

    @Autowired
    private JacksonTester<DataBookUpdate> bookUpdateJson;

    // Objects used across the testing
    DataAuthor authorData = new DataAuthor("John Doe", "7518", "john.doe@gmail.com");
    Author authorObject = new Author(authorData);
    DataBook bookData = new DataBook("Title", "03/13/2021", 30, 500, Genre.FANTASY, authorData);
    Book bookObject = new Book(bookData);
    
    @Test
    @DisplayName("Should return HTTP Status 400")
    void testRegister1() throws Exception {

        var response = mvc.perform(MockMvcRequestBuilders.post("/books/add")).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Should return HTTP Status 201")
    @SuppressWarnings("null")
    void testRegister2() throws Exception {

        DataBookListing expectedData = new DataBookListing(bookObject);

        var data = bookDataJson.write(bookData).getJson();
        var expectedJson = bookListingJson.write(expectedData).getJson();

        when(bookService.getRepository()).thenReturn(bookRepository);

        var response = mvc.perform(MockMvcRequestBuilders.post("/books/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(data))
            .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(response.getContentAsString()).isEqualTo(expectedJson);

    }

    @Test
    @DisplayName("Should return HTTP Status 200")
    void testGetById() throws Exception {

        List<Book> bookList = Arrays.asList(
            new Book(bookData)
        );

        when(bookService.toPage(any(), any())).thenCallRealMethod();
        when(bookService.searchAll()).thenReturn(bookList);
        

        var response = mvc.perform(MockMvcRequestBuilders.get("/books")).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Should return HTTP Status 200")
    @SuppressWarnings("null")
    void testUpdateBook() throws Exception {

        DataBookUpdate bookUpdate = new DataBookUpdate("New Title", 100, 200);
        var updateJson = bookUpdateJson.write(bookUpdate).getJson();        

        Book updatedBook = bookObject;
        updatedBook.setTitle("New Title");
        updatedBook.setPages(100);
        updatedBook.setPrice(200);

        DataBookListing newData = new DataBookListing(updatedBook);
        var expectedJson = bookListingJson.write(newData).getJson();

        when(bookService.searchById(any())).thenReturn(bookObject);
        doCallRealMethod().when(bookService).update(any(), any());

        var response = mvc.perform(MockMvcRequestBuilders.put("/books/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
            .andReturn()
            .getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString()).isEqualTo(expectedJson);

    }

}
