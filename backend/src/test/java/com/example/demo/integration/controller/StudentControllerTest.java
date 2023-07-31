package com.example.demo.integration.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudnetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class StudentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudnetRepository studnetRepository;

    @AfterEach
    void cleanUpEach() {
        studnetRepository.deleteAll();
    }

    @Test
    @Sql("/import_student.sql")
    void getAllStudent() {
        //give
        //when
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$").isArray())
                    .andReturn();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }
        //then
        String jsonResponse = null;
        try {
            jsonResponse = mvcResult.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }
        List<Student> studentList = null;
        try {
            studentList = objectMapper.readValue(jsonResponse, new TypeReference<List<Student>>() {
            });
        } catch (JsonProcessingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        assertThat(studentList).isNotNull();

        Student firstStudent = studentList.get(0);
        assertThat(firstStudent.getID()).isEqualTo(1);
        assertThat(firstStudent.getImie()).isEqualTo("Jan");

        Student secondStudent = studentList.get(1);
        assertThat(secondStudent.getID()).isEqualTo(2);
        assertThat(secondStudent.getNazwisko()).isEqualTo("Nowak");
    }

    @Test
    @Sql("/import_student.sql")
    void getStudentByID() {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/2"))
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").value(2))
                    .andReturn();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        Student student = null;
        try {
            student = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Student.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        assertThat(student).isNotNull();
        assertThat(student.getID()).isEqualTo(2);
        assertThat(student.getNrIndeksu()).isEqualTo("987654");

    }

    @Test
    @Sql("/import_student.sql")
    void updateStudent() {
        Student studentToUpdate = new Student();
        studentToUpdate.setImie("Hanna");
        studentToUpdate.setNazwisko("Szczygiel");

        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(studentToUpdate);
        } catch (JsonProcessingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.patch("/student/update/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }
        //when
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/2"))
                    .andExpect(status().is(200))
                    .andReturn();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        //then
        Student updatedStudent = null;
        try {
            updatedStudent = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Student.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getID()).isEqualTo(2L);
        assertThat(updatedStudent.getImie()).isEqualTo("Hanna");
        assertThat(updatedStudent.getNazwisko()).isEqualTo("Szczygiel");
    }

    @Test
    @Sql("/import_student.sql")
    void deleteStudent() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/student/delete/3"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}