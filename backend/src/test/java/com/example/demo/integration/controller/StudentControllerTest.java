package com.example.demo.integration.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudnetRepository;
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
    void getAllStudent() throws Exception {
        //give
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andReturn();
        //then
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Student> studentList = objectMapper.readValue(jsonResponse, new TypeReference<List<Student>>() {
        });

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
    void getStudentByID() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/2"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value(2))
                .andReturn();

        Student student = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Student.class);

        assertThat(student).isNotNull();
        assertThat(student.getID()).isEqualTo(2);
        assertThat(student.getNrIndeksu()).isEqualTo("987654");

    }

    @Test
    @Sql("/import_student.sql")
    void updateStudent() throws Exception {
        Student studentToUpdate = new Student();
        studentToUpdate.setImie("Hanna");
        studentToUpdate.setNazwisko("Szczygiel");

        String requestBody = objectMapper.writeValueAsString(studentToUpdate);

        mockMvc.perform(MockMvcRequestBuilders.patch("/student/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/2"))
                .andExpect(status().is(200))
                .andReturn();

        //then
        Student updatedStudent = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Student.class);

        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getID()).isEqualTo(2L);
        assertThat(updatedStudent.getImie()).isEqualTo("Hanna");
        assertThat(updatedStudent.getNazwisko()).isEqualTo("Szczygiel");
    }

    @Test
    @Sql("/import_student.sql")
    void deleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/delete/3"))
                .andExpect(status().isOk());
    }
}