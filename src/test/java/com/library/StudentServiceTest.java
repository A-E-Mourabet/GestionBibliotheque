package com.library;

import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService studentService;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize the service layer
        studentService = new StudentService();
        studentService.deleteStudent(1);
        studentService.deleteStudent(2);
        // Add students
        studentService.addStudent(new Student(1, "Alice"));
        studentService.addStudent(new Student(2, "Bob"));
    }

    @Test
    void testAddStudent() throws SQLException {
        // Test if the student is added correctly
        assertEquals(2, studentService.getAllStudents().size()); // 2 students should be added
        assertEquals("Alice", studentService.findStudentById(1).getName()); // Verify Alice's name
    }

    @Test
    void testUpdateStudent() throws SQLException {
        // Update Alice's details
        studentService.updateStudent(new Student(1, "Alice Smith"));

        // Verify the update
        assertEquals("Alice Smith", studentService.findStudentById(1).getName()); // Alice's name should be updated
    }

    @Test
    void testDeleteStudent() throws SQLException {
        // Delete Alice
        studentService.deleteStudent(1);

        // Verify that Alice is deleted
        assertNull(studentService.findStudentById(1)); // Should return empty since Alice is deleted
    }

    @Test
    void testGetAllStudents() throws SQLException {
        // Add another student
        studentService.addStudent(new Student(3, "Charlie"));

        // Verify the number of students
        assertEquals(3, studentService.getAllStudents().size()); // 3 students should now exist
    }
}
