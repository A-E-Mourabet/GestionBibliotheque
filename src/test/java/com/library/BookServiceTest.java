package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookDAO bookDAO; // Mock the DAO

    @InjectMocks
    private BookService bookService; // Inject the mock DAO into the service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testAddBook() {
        Book book = new Book("Java Programming", "John Doe", "ENSA-MA", 2023);

        // Mock the DAO behavior
        doNothing().when(bookDAO).add(book);
        when(bookDAO.getAllBooks()).thenReturn(List.of(book));
        when(bookDAO.getBookByTitle("Java Programming")).thenReturn(book);

        // Test the service method
        bookService.addBook(book);

        // Verify interactions with the DAO
        verify(bookDAO, times(1)).add(book);
        assertEquals(1, bookDAO.getAllBooks().size());
        assertEquals("Java Programming", bookService.findBookByTitle("Java Programming").getTitle());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book("Java Programming", "John Doe", "ENSA-MA", 2023);

        // Mock the DAO behavior
        when(bookDAO.getBookByid(book.getId())).thenReturn(book);
        doNothing().when(bookDAO).update(book);

        // Test the service method
        book.setTitle("Advanced Java");
        bookService.updateBook(book);

        // Verify interactions with the DAO
        verify(bookDAO, times(1)).update(book);
        assertEquals("Advanced Java", book.getTitle());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book("Java Programming", "John Doe", "ENSA-MA", 2023);

        // Mock the DAO behavior
        when(bookDAO.getBookByTitle("Java Programming")).thenReturn(book);
        doNothing().when(bookDAO).delete(book.getId());

        // Test the service method
        bookService.deleteBook(book.getId());

        // Verify interactions with the DAO
        verify(bookDAO, times(1)).delete(book.getId());
        when(bookDAO.getBookByTitle("Java Programming")).thenReturn(null);
        assertNull(bookService.findBookByTitle("Java Programming"));
    }
}
