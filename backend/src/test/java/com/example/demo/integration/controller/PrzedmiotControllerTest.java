package com.example.demo.integration.controller;

import com.example.demo.model.Przedmiot;
import com.example.demo.repository.PrzedmiotRepository;
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
    void shouldGetAllPrzedmiot() throws Exception {
        //give
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/przedmiot"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andReturn();
        //then
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Przedmiot> przedmiotList = objectMapper.readValue(jsonResponse, new TypeReference<List<Przedmiot>>() {
        });

        assertThat(przedmiotList).isNotNull();

        Przedmiot pierwszyPrzedmiot = przedmiotList.get(0);
        assertThat(pierwszyPrzedmiot.getID()).isEqualTo(1);
        assertThat(pierwszyPrzedmiot.getNazwa()).isEqualTo("Matematyka");

        Przedmiot drugiPrzedmiot = przedmiotList.get(1);
        assertThat(drugiPrzedmiot.getID()).isEqualTo(2);
        assertThat(drugiPrzedmiot.getNazwa()).isEqualTo("Informatyka");
    }

    @Test
    @Sql("/import_przedmiot.sql")
    void shouldGetSinglePrzedmiot() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/przedmiot/2"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nazwa").value("Informatyka"))
                .andReturn();

        Przedmiot przedmiot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Przedmiot.class);

        assertThat(przedmiot).isNotNull();
        assertThat(przedmiot.getID()).isEqualTo(2);
        assertThat(przedmiot.getNazwa()).isEqualTo("Informatyka");

    }

    @Test
    @Sql("/import_przedmiot.sql")
    void shouldUpdatePrzedmiotByID() throws Exception {
        //given
        Przedmiot przedmiotToUpdate = new Przedmiot();
        przedmiotToUpdate.setNazwa("Matematyka II");

        String requestBody = objectMapper.writeValueAsString(przedmiotToUpdate);

        mockMvc.perform(MockMvcRequestBuilders.patch("/przedmiot/update/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/przedmiot/3"))
                .andExpect(status().is(200))
                .andReturn();

        //then
        Przedmiot updatedPrzedmiot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Przedmiot.class);

        assertThat(updatedPrzedmiot).isNotNull();
        assertThat(updatedPrzedmiot.getID()).isEqualTo(3L);
        assertThat(updatedPrzedmiot.getNazwa()).isEqualTo("Matematyka II");
    }

    @Test
    @Sql("/import_przedmiot.sql")
    void shouldDeletePrzedmiotByID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/przedmiot/delete/3"))
                .andExpect(status().isOk());
    }
}