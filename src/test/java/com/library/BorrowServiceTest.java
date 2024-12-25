package com.library;

import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class BorrowServiceTest {
    private BookService bookService;
    private StudentService studentService;
    private BorrowService borrowService;
    private Book book;
    private Student student;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize the service layers
        bookService = new BookService();
        studentService = new StudentService();
        borrowService = new BorrowService();


        // Add students
        studentService.addStudent(new Student(1, "Alice"));
        studentService.addStudent(new Student(2, "Bob"));

        // Add books
        bookService.addBook(new Book("Java Programming", "John Doe", "ENSA-MA", 2023));
        bookService.addBook(new Book("Advanced Java", "John Doe", "ENSA-MA", 2023));

        // Fetch the book and student for testing
        book = bookService.findBookByTitle("Java Programming");
        student = studentService.findStudentById(1);

    }

    @Test
    void testBorrowBook() throws SQLException {
        // Use LocalDate to handle the borrow date
        LocalDate borrowDate = LocalDate.of(2024, 11, 12);  // Set borrow date (12/11/2024)
        Borrow borrow = new Borrow(1,student, book, java.sql.Date.valueOf(borrowDate), java.sql.Date.valueOf(borrowDate));
        borrowService.borrowBook(borrow);

        // Verify the book's availability after borrowing
        assertNull(bookService.findBookById(borrow.getBook().getId()), "The book should not be available after borrowing.");
        studentService.deleteStudent(1);
        studentService.deleteStudent(2);
        bookService.deleteBook(bookService.findBookByTitle("Advanced Java").getId());
        borrowService.deleteBorrow(borrow);


    }

    @Test
    void testReturnBook() throws SQLException {
        // Alice borrows the book
        LocalDate borrowDate = LocalDate.of(2024, 11, 12);  // Set borrow date (12/11/2024)
        Borrow borrow = new Borrow(1,student, book, java.sql.Date.valueOf(borrowDate));
        borrowService.borrowBook(borrow);

        // Return the book
        borrowService.returnBook(borrow);

        // Verify the book's availability after returning
        assertNull(bookService.findBookById(borrow.getBook().getId()), "The book should be available after returning.");

        studentService.deleteStudent(1);
        studentService.deleteStudent(2);
        bookService.deleteBook(bookService.findBookByTitle("Java Programming").getId());
        bookService.deleteBook(bookService.findBookByTitle("Advanced Java").getId());
        borrowService.deleteBorrow(borrow);
    }


    @Test
    void testBorrowBookStudentNotFound() throws SQLException {
        // Try borrowing with a non-existent student (ID 3)
        Student student3 = studentService.findStudentById(3); // Should return null or throw exception
        Book book = bookService.findBookById(1);
        LocalDate borrowDate = LocalDate.of(2024, 11, 12);  // Set borrow date (12/11/2024)
        assertNull(student3, "The student should not be found.");
        assertThrows(NullPointerException.class, () -> borrowService.borrowBook(new Borrow(1,student3, book,java.sql.Date.valueOf(borrowDate))));

        studentService.deleteStudent(1);
        studentService.deleteStudent(2);
        bookService.deleteBook(bookService.findBookByTitle("Java Programming").getId());
        bookService.deleteBook(bookService.findBookByTitle("Advanced Java").getId());
    }
}
