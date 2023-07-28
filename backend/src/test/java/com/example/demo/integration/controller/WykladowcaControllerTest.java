package com.example.demo.integration.controller;

import com.example.demo.model.Wykladowca;
import com.example.demo.repository.WykladowcaRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class WykladowcaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WykladowcaRepository wykladowcaRepository;

    @AfterEach
    void cleanUpEach() {
        wykladowcaRepository.deleteAll();
    }

    @Test
    @Sql("/import_wykladowca.sql")
    void getAllWykladowca() throws Exception {
        // Given
        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/wykladowca"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();

        // Then
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Wykladowca> wykladowcaList = objectMapper.readValue(jsonResponse, new TypeReference<List<Wykladowca>>() {
        });

        assertThat(wykladowcaList).isNotNull();
        assertThat(wykladowcaList).hasSize(3);

        Wykladowca firstWykladowca = wykladowcaList.get(0);
        assertThat(firstWykladowca.getID()).isEqualTo(1);
        assertThat(firstWykladowca.getImie()).isEqualTo("Profesor");
        assertThat(firstWykladowca.getNazwisko()).isEqualTo("Smith");
        assertThat(firstWykladowca.getStopien()).isEqualTo("Doktor");

    }

    @Test
    @Sql("/import_wykladowca.sql")
    void getWykladowcaByID() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/wykladowca/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andReturn();

        Wykladowca wykladowca = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Wykladowca.class);

        assertThat(wykladowca).isNotNull();
        assertThat(wykladowca.getID()).isEqualTo(2);
        assertThat(wykladowca.getImie()).isEqualTo("Dr");
        assertThat(wykladowca.getNazwisko()).isEqualTo("Johnson");
        assertThat(wykladowca.getStopien()).isEqualTo("Magister");
    }

    @Test
    @Sql("/import_wykladowca.sql")
    void updateStudent() throws Exception {
        // Given
        Wykladowca wykladowcaToUpdate = new Wykladowca();
        wykladowcaToUpdate.setImie("New Name");
        wykladowcaToUpdate.setNazwisko("New Surname");
        wykladowcaToUpdate.setStopien("Inzynier");

        String requestBody = objectMapper.writeValueAsString(wykladowcaToUpdate);

        // When
        mockMvc.perform(MockMvcRequestBuilders.patch("/wykladowca/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        // Then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/wykladowca/1"))
                .andExpect(status().isOk())
                .andReturn();

        Wykladowca updatedWykladowca = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Wykladowca.class);

        assertThat(updatedWykladowca).isNotNull();
        assertThat(updatedWykladowca.getID()).isEqualTo(1L);
        assertThat(updatedWykladowca.getImie()).isEqualTo("New Name");
        assertThat(updatedWykladowca.getNazwisko()).isEqualTo("New Surname");
    }

    @Test
    @Sql("/import_wykladowca.sql")
    void deleteWykladowca() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/wykladowca/delete/2"))
                .andExpect(status().isOk());
    }

}
