package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.CathedraForm;
import com.gmail.sergick6690.service.CathedraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {CathedraRestController.class})
class CathedraRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private CathedraService cathedraService;
    private static final String API_CATHEDRAS_URL = "/api/cathedras";
    private static final String API_CATHEDRAS_LIST_URL = "/api/cathedras/list";

    @Test
    void add() throws Exception {
        mockMvc.perform(post(API_CATHEDRAS_URL).
                content(objectMapper.writeValueAsString(new CathedraForm("Test", 1))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllCathedras() throws Exception {
        mockMvc.perform(get(API_CATHEDRAS_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(API_CATHEDRAS_URL + "/{cathedra-id}", 1)
                .contentType("application/json").param("cathedra-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteCatrhedra() throws Exception {
        mockMvc.perform(delete(API_CATHEDRAS_URL + "/{cathedra-id}", 1)
                .contentType("application/json").param("cathedra-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}