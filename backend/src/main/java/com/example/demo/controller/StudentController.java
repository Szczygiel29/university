package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.sercive.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @Operation(summary = "Get all students", description = "Retrieve a list of all students from the database.")
    @GetMapping
    public List<Student> getAllStudent() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the student."),
            @ApiResponse(responseCode = "404", description = "Student not found.")
    })
    @Operation(summary = "Get student by ID", description = "Retrieve a list of available products from the database.")
    public Student getStudentByID(
            @Parameter(description = "ID of the student to be retrieved.", required = true)
            @PathVariable long id) {
        return studentService.getStudent(id);
    }

    @Operation(summary = "Add a new student", description = "Add a new student to the database.")
    @PostMapping
    public boolean addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @Operation(summary = "Update student by ID", description = "Update an existing student in the database.")
    @PatchMapping("/update/{id}")
    public boolean updateStudnet(
            @Parameter(description = "ID of the student to be updated.", required = true)
            @RequestBody Student student,
            @PathVariable long id) {
        return studentService.editStudent(student, id);
    }

    @Operation(summary = "Delete student by ID", description = "Delete a student from the database by their ID.")
    @DeleteMapping("/delete/{id}")
    public boolean deleteStudent(
            @Parameter(description = "ID of the student to be deleted.", required = true)
            @PathVariable long id) {
        return studentService.deleteStudent(id);
    }

}
