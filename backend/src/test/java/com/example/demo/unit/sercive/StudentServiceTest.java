package com.example.demo.unit.sercive;

import com.example.demo.model.Student;
import com.example.demo.repository.StudnetRepository;
import com.example.demo.sercive.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudnetRepository studnetRepository;

    @Test
    public void getAllStudent(){
        List<Student> sampleStudents = List.of(
                new Student(1L, "Jarek", "Smith", "312321", null),
                new Student(2L, "Jane", "Bond", "43255", null),
                new Student(3L, "Bob", "Larry", "23523", null)
        );

        when(studnetRepository.findAll()).thenReturn(sampleStudents);

        List<Student> result = studentService.getAllStudents();

        Assertions.assertEquals(sampleStudents, result);
    }

    @Test
    public void testGetStudent_ExistingStudent() {
        long existingStudentId = 1L;
        String existingFirstName = "Jarek";
        String existingLastName = "Smith";
        String existingStudentIdNumber = "312321";
        Student existingStudent = new Student(existingStudentId, existingFirstName, existingLastName, existingStudentIdNumber, null);

        when(studnetRepository.findById(existingStudentId)).thenReturn(Optional.of(existingStudent));

        Student result = studentService.getStudent(existingStudentId);

        Assertions.assertEquals(existingStudent, result);
    }

    @Test
    public void testGetStudent_NonExistingStudent() {
        long nonExistingStudentId = 99L;
        when(studnetRepository.findById(nonExistingStudentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            studentService.getStudent(nonExistingStudentId);
        });
    }

    @Test
    public void testEditStudent_ExistingStudent() {
        long existingStudentId = 1L;
        String updatedFirstName = "Updated";
        String updatedLastName = "Student";
        String updatedStudentIdNumber = "555666";

        Student existingStudent = new Student(existingStudentId, "John", "Doe", "123456", null);
        when(studnetRepository.findById(existingStudentId)).thenReturn(Optional.of(existingStudent));

        Student updatedStudent = new Student(existingStudentId, updatedFirstName, updatedLastName, updatedStudentIdNumber, null);
        when(studnetRepository.save(updatedStudent)).thenReturn(updatedStudent);

        boolean isEdited = studentService.editStudent(updatedStudent, existingStudentId);

        assertTrue(isEdited);
        assertEquals(updatedFirstName, existingStudent.getImie());
        assertEquals(updatedLastName, existingStudent.getNazwisko());
        assertEquals(updatedStudentIdNumber, existingStudent.getNrIndeksu());
    }

    @Test
    public void testEditStudent_NonExistingStudent() {
        long nonExistingStudentId = 99L;
        Student updatedStudent = new Student(nonExistingStudentId, "Updated", "Student", "555666", null);
        when(studnetRepository.findById(nonExistingStudentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            studentService.editStudent(updatedStudent, nonExistingStudentId);
        });
    }

    @Test
    public void testDeleteStudent_ExistingStudent() {
        long existingStudentId = 1L;
        Student existingStudent = new Student(existingStudentId, "John", "Doe", "123456", null);
        when(studnetRepository.findById(existingStudentId)).thenReturn(Optional.of(existingStudent));

        boolean isDeleted = studentService.deleteStudent(existingStudentId);

        assertTrue(isDeleted);
    }

    @Test
    public void testDeleteStudent_NonExistingStudent() {
        long nonExistingStudentId = 99L;
        when(studnetRepository.findById(nonExistingStudentId)).thenReturn(Optional.empty());

        boolean isDeleted = studentService.deleteStudent(nonExistingStudentId);

        assertFalse(isDeleted);
    }


}