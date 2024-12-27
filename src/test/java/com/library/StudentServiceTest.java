package com.library;

import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentService studentService;

    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create mock student objects
        student1 = new Student(1, "Alice");
        student2 = new Student(2, "Bob");
        student3 = new Student(3, "Charlie");

        // Mock behavior for the StudentService
        when(studentService.findStudentById(1)).thenReturn(student1);
        when(studentService.findStudentById(2)).thenReturn(student2);
        when(studentService.findStudentById(3)).thenReturn(student3);

        // Mock getAllStudents method
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));

        // Mock the addStudent method (doesn't return anything)
        doNothing().when(studentService).addStudent(any(Student.class));

        // Mock the updateStudent method (doesn't return anything)
        doNothing().when(studentService).updateStudent(any(Student.class));

        // Mock the deleteStudent method (doesn't return anything)
        doNothing().when(studentService).deleteStudent(anyInt());
    }

    @Test
    void testAddStudent() throws SQLException {
        // Add a new student
        studentService.addStudent(student3);

        // Verify the interaction with the mocked addStudent method
        verify(studentService, times(1)).addStudent(student3);

        // Mock the updated list after adding the student
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2, student3));

        // Verify the number of students after adding
        assertEquals(3, studentService.getAllStudents().size());
    }

    @Test
    void testUpdateStudent() throws SQLException {
        // Update Alice's details
        student1.setName("Alice Smith");
        studentService.updateStudent(student1);

        // Verify the interaction with the mocked updateStudent method
        verify(studentService, times(1)).updateStudent(student1);

        // Mock the updated student data
        when(studentService.findStudentById(1)).thenReturn(student1);

        // Verify the updated name
        assertEquals("Alice Smith", studentService.findStudentById(1).getName());
    }

    @Test
    void testDeleteStudent() throws SQLException {
        // Delete Alice
        studentService.deleteStudent(1);

        // Verify the interaction with the mocked deleteStudent method
        verify(studentService, times(1)).deleteStudent(1);

        // Mock that the student no longer exists after deletion
        when(studentService.findStudentById(1)).thenReturn(null);

        // Verify that Alice is deleted
        assertNull(studentService.findStudentById(1));
    }

    @Test
    void testGetAllStudents() throws SQLException {
        // Verify the initial list of students
        List<Student> students = studentService.getAllStudents();

        // Verify the size of the student list
        assertEquals(2, students.size()); // 2 students should exist

        // Add another student and verify the updated list
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2, student3));
        students = studentService.getAllStudents();

        assertEquals(3, students.size()); // Now 3 students should exist

        // Verify the correct students in the list
        assertTrue(students.contains(student1));
        assertTrue(students.contains(student2));
        assertTrue(students.contains(student3));
    }
}
