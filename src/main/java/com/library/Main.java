package com.library;

import com.library.service.BorrowService;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.dao.BorrowDAO;  // Import BorrowDAO

import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Create services
        BookService bookService = new BookService();
        StudentService studentService = new StudentService();
        BorrowDAO borrowDAO = new BorrowDAO();  // Create BorrowDAO
        BorrowService borrowService = new BorrowService();  // Pass BorrowDAO to BorrowService constructor

        // Adding some sample books and students
        bookService.addBook(new Book("Effective Java", "Joshua Bloch", "123456", 2017));
        bookService.addBook(new Book("Clean Code", "Robert C. Martin", "654321", 2008));

        studentService.addStudent(new Student(1, "John Doe"));
        studentService.addStudent(new Student(2, "Jane Smith"));

        boolean running = true;

        while (running) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher les livres");
            System.out.println("3. Ajouter un étudiant");
            System.out.println("4. Afficher les étudiants");
            System.out.println("5. Emprunter un livre");
            System.out.println("6. Afficher les emprunts");
            System.out.println("7. Retourner un livre");
            System.out.println("8. Quitter");

            System.out.print("Choisir une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Entrez le titre du livre: ");
                    String title = scanner.nextLine();
                    System.out.print("Entrez l'auteur du livre: ");
                    String author = scanner.nextLine();
                    System.out.print("Entrez le code ISBN du livre: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Entrez l'année de publication: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Book newBook = new Book(title, author, isbn, year);
                    bookService.addBook(newBook);
                    System.out.println("Livre ajouté avec succès!");
                    break;

                case 2:
                    System.out.println("Liste des livres:");
                    bookService.displayBooks();
                    break;

                case 3:
                    System.out.print("Entrez le nom de l'étudiant: ");
                    String studentName = scanner.nextLine();
                    Student newStudent = new Student(studentName);
                    studentService.addStudent(newStudent);
                    System.out.println("Étudiant ajouté avec succès!");
                    break;

                case 4:
                    System.out.println("Liste des étudiants:");
                    studentService.displayStudents();
                    break;

                case 5:
                    System.out.print("Entrez l'ID de l'étudiant: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Entrez l'ID du livre: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Student studentForBorrow = studentService.findStudentById(studentId);
                    Book bookForBorrow = bookService.findBookById(bookId);

                    break;

                case 6:
                    System.out.println("Liste des emprunts:");
                    borrowService.displayBorrows();
                    break;

                case 7:
                    System.out.print("Entrez l'ID de l'étudiant: ");
                    int studentIdForReturn = scanner.nextInt();
                    System.out.print("Entrez l'ID du livre à retourner: ");
                    int bookIdForReturn = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Student studentForReturn = studentService.findStudentById(studentIdForReturn);
                    Book bookForReturn = bookService.findBookById(bookIdForReturn);

                    break;

                case 8:
                    running = false;
                    System.out.println("Au revoir!");
                    break;

                default:
                    System.out.println("Option invalide.");
            }
        }

        scanner.close();
    }
}
