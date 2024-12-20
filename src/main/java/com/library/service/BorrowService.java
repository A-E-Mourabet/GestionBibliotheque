
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

    private BorrowDAO borrowDAO;

    // Constructeur avec BorrowDAO
    public BorrowService() {
    }

    // Méthode pour emprunter un livre
    public void borrowBook(Borrow borrow) {
        // Sauvegarde de l'emprunt dans la base de données
        borrowDAO.save(borrow);
    }

    // Method to return a book
    public void returnBook(Borrow borrow) throws SQLException {
        // Update the borrow record (optional, based on how you handle the return date)
        borrow.setReturnDate(java.sql.Date.valueOf(java.time.LocalDate.now())); // Set the current date as return date
        borrowDAO.save(borrow); // Update the borrow record in the database
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

}
