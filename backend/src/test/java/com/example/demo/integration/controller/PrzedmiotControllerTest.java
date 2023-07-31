package com.example.demo.integration.controller;

import com.example.demo.model.Przedmiot;
import com.example.demo.repository.PrzedmiotRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
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
class PrzedmiotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PrzedmiotRepository przedmiotRepository;

    @AfterEach
    void cleanUpEach() {
        przedmiotRepository.deleteAll();
    }

    @Test
    @Sql("/import_przedmiot.sql")
    void shouldGetAllPrzedmiot()  {
        //give
        //when
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/przedmiot"))
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

        List<Przedmiot> przedmiotList = null;
        try {
            przedmiotList = objectMapper.readValue(jsonResponse, new TypeReference<List<Przedmiot>>() {
            });
        } catch (JsonProcessingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        assertThat(przedmiotList).isNotNull();

        Przedmiot pierwszyPrzedmiot = przedmiotList.get(0);
        assertThat(pierwszyPrzedmiot.getID()).isEqualTo(1);
        assertThat(pierwszyPrzedmiot.getNazwa()).isEqualTo("Matematyka");

        Przedmiot drugiPrzedmiot = przedmiotList.get(1);
        assertThat(drugiPrzedmiot.getID()).isEqualTo(2);
        assertThat(drugiPrzedmiot.getNazwa()).isEqualTo("Informatyka");
    }

    //TODO
    @Test
    @Sql("/import_przedmiot.sql")
    void shouldGetSinglePrzedmiot() {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/przedmiot/2"))
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").value(2))
                    .andExpect(jsonPath("$.nazwa").value("Informatyka"))
                    .andReturn();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        Przedmiot przedmiot = null;
        try {
            przedmiot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Przedmiot.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        assertThat(przedmiot).isNotNull();
        assertThat(przedmiot.getID()).isEqualTo(2);
        assertThat(przedmiot.getNazwa()).isEqualTo("Informatyka");

    }

    @Test
    @Sql("/import_przedmiot.sql")
    void shouldUpdatePrzedmiotByID() {
        //given
        Przedmiot przedmiotToUpdate = new Przedmiot();
        przedmiotToUpdate.setNazwa("Matematyka II");

        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(przedmiotToUpdate);
        } catch (JsonProcessingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.patch("/przedmiot/update/3")
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
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/przedmiot/3"))
                    .andExpect(status().is(200))
                    .andReturn();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        //then
        Przedmiot updatedPrzedmiot = null;
        try {
            updatedPrzedmiot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Przedmiot.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }

        assertThat(updatedPrzedmiot).isNotNull();
        assertThat(updatedPrzedmiot.getID()).isEqualTo(3L);
        assertThat(updatedPrzedmiot.getNazwa()).isEqualTo("Matematyka II");
    }

    @Test
    @Sql("/import_przedmiot.sql")
    void shouldDeletePrzedmiotByID() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/przedmiot/delete/3"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}