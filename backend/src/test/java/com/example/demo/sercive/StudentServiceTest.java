package com.example.demo.sercive;

import com.example.demo.model.Student;
import com.example.demo.repository.StudnetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudnetRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Jan", "Kowalski", "1234567890", null));
        students.add(new Student(2L, "Anna", "Nowak", "0987654321", null));

        when(studentRepository.findAll()).thenReturn(students);
        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetStudent_ExistingId_ShouldReturnStudent() {
        long studentId = 1L;
        Student student = new Student(studentId, "Jan", "Kowalski", "1234567890", null);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Student result = studentService.getStudent(studentId);

        assertNotNull(result);
        assertEquals(studentId, result.getID());
    }

    @Test
    public void testGetStudent_NonExistingId_ShouldThrowEntityNotFoundException() {
        long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.getStudent(studentId));
    }

    @Test
    public void testEditStudent_ExistingId_ShouldSaveAndReturnTrue() {
        long studentId = 1L;
        Student existingStudent = new Student(studentId, "Jan", "Kowalski", "1234567890", null);
        Student updatedStudent = new Student(studentId, "Anna", "Nowak", "0987654321", null);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        boolean result = studentService.editStudent(updatedStudent, studentId);

        assertTrue(result);
    }

    @Test
    public void testEditStudent_NonExistingId_ShouldThrowEntityNotFoundException() {
        long studentId = 1L;
        Student updatedStudent = new Student(studentId, "Anna", "Nowak", "0987654321", null);

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.editStudent(updatedStudent, studentId));
    }

    @Test
    public void testDeleteStudent_ExistingId_ShouldDeleteAndReturnTrue() {
        long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
        boolean result = studentService.deleteStudent(studentId);

        assertTrue(result);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    public void testDeleteStudent_NonExistingId_ShouldReturnFalse() {
        long studentId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        boolean result = studentService.deleteStudent(studentId);

        assertFalse(result);
        verify(studentRepository, never()).deleteById(studentId);
    }
}
