package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.sercive.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudnetController {
     private final StudentService studentService;

    public StudnetController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudnt() {
        return studentService.getAllStudents();
    }

    @GetMapping("/id")
    public Student getStudentByID(@PathVariable long id){
        return studentService.getStudent(id);
    }

    @PostMapping
    public boolean addStudnet(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @PatchMapping("/update/{id}")
    public boolean updateStudnet(@RequestBody Student student, @PathVariable long id){
        return studentService.editStudent(student, id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteStudent(@PathVariable long id){
        return studentService.deleteStudent(id);
    }
}
