package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    // Default constructor
    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    // Constructor with dependency injection
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // Ajouter un étudiant
    public void addStudent(Student student) throws SQLException {
        studentDAO.addStudent(student);
    }

    // Afficher tous les étudiants
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    // Trouver un étudiant par ID
    public Student findStudentById(int id) throws SQLException {
        return studentDAO.getStudentById(id);
    }

    // Display all students (can be part of a UI or separate logic)
    public void displayStudents() throws SQLException {
        List<Student> students = getAllStudents();
        for (Student student : students) {
            System.out.println("ID: " + student.getId() + " | Nom: " + student.getName());
        }
    }

    // Delete student
    public void deleteStudent(int id) {
        studentDAO.deleteStudent(id);
    }

    public void updateStudent(Student student){
        studentDAO.updateStudent(student);
    }

}
