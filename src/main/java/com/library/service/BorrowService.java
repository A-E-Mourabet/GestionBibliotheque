
package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

import java.sql.SQLException;
import java.util.List;

public class BorrowService {

    public BorrowDAO borrowDAO = new BorrowDAO();
    public BookService bookService= new BookService();
    public StudentService studentService= new StudentService();



    // Constructeur avec BorrowDAO
    public BorrowService() {
    }

    // Méthode pour emprunter un livre
    public void borrowBook(Borrow borrow) {
        if (borrow.getStudent() == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (borrow.getBook() == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        // Check if the book exists
        Book book = bookService.findBookById(borrow.getBook().getId());
        if (book == null) {
            throw new IllegalArgumentException("Book not found in the database.");
        }

        // Check if the student exists
        Student student = studentService.findStudentById(borrow.getStudent().getId());
        if (student == null) {
            throw new IllegalArgumentException("Student not found in the database.");
        }

        // Save the borrow record and delete the book from the database
        borrowDAO.save(borrow);
        bookService.deleteBook(book.getId());
    }


    // Method to return a book
    public void returnBook(Borrow borrow) throws SQLException {
        // Update the borrow record (optional, based on how you handle the return date)
        borrow.setReturnDate(java.sql.Date.valueOf(java.time.LocalDate.now()));// Set the current date as return date
        borrowDAO.deleteBorrow(borrow.getId());
        borrowDAO.save(borrow); // Update the borrow record in the database
        bookService.addBook(borrow.getBook());
    }

    // Afficher les emprunts (méthode fictive, à adapter)
    public void displayBorrows() {
        System.out.println("Liste des emprunts...");
        BorrowDAO borrowDAO = new BorrowDAO(); // Create an instance of BorrowDAO
        List<Borrow> borrows = borrowDAO.getAllBorrows(); // Fetch all borrows
        for (Borrow borrow : borrows) {
            System.out.println("ID: " + borrow.getId() +
                    ", Étudiant: " + borrow.getStudent() +
                    ", Livre: " + borrow.getBook() +
                    ", Date d'emprunt: " + borrow.getBorrowDate() +
                    ", Date de retour: " + borrow.getReturnDate());
        }
    }

    public void deleteBorrow(Borrow borrow){
        borrowDAO.deleteBorrow(borrow.getId());
    }

}