package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO(); // This will be replaced with a mock in real tests
        bookService = new BookService(); // Inject the DAO into the service
    }

    @Test
    void testAddBook() {
        Book book = new Book("Java Programming", "John Doe", "ENSA-MA", 2023);
        bookService.addBook(book);

        // Verify that the book was added
        assertEquals(1, bookDAO.getAllBooks().size());
        assertEquals("Java Programming", bookService.findBookById(1).getTitle());
        bookService.deleteBook(1);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book("Java Programming", "John Doe", "ENSA-MA", 2023);
        bookService.addBook(book);

        // Update the book
        Book bookToUpdate = bookService.findBookById(1);
        bookToUpdate.setTitle("Advanced Java");
        bookService.updateBook(bookToUpdate);

        // Verify the update
        assertEquals("Advanced Java", bookService.findBookById(1).getTitle());
        assertNotEquals(book.getTitle(), bookService.findBookById(1).getTitle());
        bookService.deleteBook(1);

    }

    @Test
    void testDeleteBook() {
        Book book = new Book("Java Programming", "John Doe", "ENSA-MA", 2023);
        bookService.addBook(book);

        // Delete the book
        bookService.deleteBook(1);

        // Verify that the book is deleted (it should return null or throw an exception)
        assertNull(bookService.findBookById(1));
    }
}
