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

    // Add a student
    public void addStudent(Student student) {
        if (student == null || student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid student details.");
        }
        studentDAO.addStudent(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    // Find a student by ID
    public Student findStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    // Display all students
    public void displayStudents() {
        List<Student> students = getAllStudents();
        if (students != null) {
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + " | Name: " + student.getName());
            }
        } else {
            System.err.println("No students found.");
        }
    }

    // Delete a student
    public void deleteStudent(int id) {
        try {
            studentDAO.deleteStudent(id);
        } catch (Exception e) {
            System.err.println("Error while deleting student: " + e.getMessage());
        }
    }

    // Update student details
    public void updateStudent(Student student) {
        if (student == null || student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid student details.");
        }
        try {
            studentDAO.updateStudent(student);
        } catch (Exception e) {
            System.err.println("Error while updating student: " + e.getMessage());
        }
    }
}
