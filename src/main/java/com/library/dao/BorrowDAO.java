package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Fetch the student object by querying the database (using the student ID)
                int studentId = rs.getInt("student");
                Student student = new StudentService().findStudentById(studentId);
                // Fetch the Book object by querying the database (using the Book ID)
                int bookId = rs.getInt("book");
                Book book = new BookService().findBookById(bookId);

                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        student,  // Use the Student object
                        book,
                        rs.getDate("borrowdate"),
                        rs.getDate("returndate")
                );
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    public void save(Borrow borrow) {
        String query;
        if (borrow.getId() == 0) {
            query = "INSERT INTO borrows (id, idstudent,idbook, borrowdate, returndate) VALUES (?, ?, ?, ?, ?)";
        } else {
            query = "UPDATE borrows SET idstudent = ?, idbook = ?, borrowdate = ?, returndate = ? WHERE id = ?";
        }

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, borrow.getStudent().getId());  // Use student.getId() instead of student ID directly
            stmt.setObject(2, borrow.getStudent());
            stmt.setObject(3, borrow.getBook());
            stmt.setDate(4, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(5, new java.sql.Date(borrow.getReturnDate().getTime()));

            if (borrow.getId() != 0) {
                stmt.setInt(5, borrow.getId());
            }

            stmt.executeUpdate();

            if (borrow.getId() == 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        borrow.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBorrow(Borrow borrow) {
        String query = "INSERT INTO borrows (id, idstudent,idbook, borrowdate, returndate) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setObject(2, borrow.getStudent());
            stmt.setObject(3, borrow.getBook());
            stmt.setDate(4, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(5, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBorrow(int borrowId) {
        String query = "DELETE FROM borrows WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrowId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
