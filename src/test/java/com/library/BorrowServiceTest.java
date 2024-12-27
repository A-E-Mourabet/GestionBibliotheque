package com.library;

import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowServiceTest {
    @Mock
    private BookService bookService; // Mocking BookService

    @Mock
    private StudentService studentService; // Mocking StudentService

    @InjectMocks
    private BorrowService borrowService; // Injecting mocks into BorrowService

    private Book book;
    private Student student;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create mock book and student
        book = new Book("Java Programming", "John Doe", "ENSA-MA", 2023);
        book.setId(1); // Ensure the book has an ID that matches the test case
        student = new Student(1, "Alice");

        // Mock service behavior
        when(studentService.findStudentById(1)).thenReturn(student); // Mock existing student
        when(studentService.findStudentById(3)).thenReturn(null);  // Mock non-existent student
        when(bookService.findBookById(1)).thenReturn(book); // Mock book lookup by ID
        when(bookService.findBookByTitle("Java Programming")).thenReturn(book); // Mock book lookup by title
        when(bookService.findBookById(2)).thenReturn(null);  // Mock non-existent book lookup
    }

    @Test
    void testBorrowBook() throws SQLException {
        // Setup borrow date
        LocalDate borrowDate = LocalDate.of(2024, 11, 12);
        Borrow borrow = new Borrow(1, student, book, java.sql.Date.valueOf(borrowDate), java.sql.Date.valueOf(borrowDate));

        // Call borrowBook method (it should not interact with actual DB)
        borrowService.borrowBook(borrow);

        // Verify interactions with mocked services
        verify(bookService, times(1)).findBookById(1);  // Ensure the method was called
        verify(bookService, times(1)).deleteBook(1);   // Ensure the book was deleted from the service
        verify(studentService, times(1)).findStudentById(1); // Ensure the student was found
    }

    @Test
    void testReturnBook() throws SQLException {
        Book book = bookService.findBookById(1);
        // Setup borrow date
        LocalDate borrowDate = LocalDate.of(2024, 11, 12);
        Borrow borrow = new Borrow(1, student, book, java.sql.Date.valueOf(borrowDate));

        // Call returnBook method (it should not interact with actual DB)
        borrowService.returnBook(borrow);

        // Verify interactions with mocked services
        verify(bookService, times(1)).findBookById(1);  // Ensure the method was called to find the book
        verify(bookService, times(1)).addBook(book);   // Ensure the book was added back to the service
    }

    @Test
    void testBorrowBookStudentNotFound() throws SQLException {
        // Try borrowing with a non-existent student (ID 3)
        Student student3 = studentService.findStudentById(3); // Should return null or throw exception
        Book book = bookService.findBookById(1);
        LocalDate borrowDate = LocalDate.of(2024, 11, 12);

        assertNull(student3, "The student should not be found.");

        // Ensure that IllegalArgumentException is thrown when trying to borrow with a non-existent student
        assertThrows(IllegalArgumentException.class, () -> borrowService.borrowBook(new Borrow(1, student3, book, java.sql.Date.valueOf(borrowDate))));
    }
}
