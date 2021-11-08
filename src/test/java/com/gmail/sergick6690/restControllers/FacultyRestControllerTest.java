package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.FacultyForm;
import com.gmail.sergick6690.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {FacultyRestController.class})
@ActiveProfiles("test")
class FacultyRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private FacultyService facultyService;
    private static final String API_FACULTIES_URL = "/api/faculties";
    private static final String API_FACULTIES_LIST_URL = "/api/faculties/list";

    @Test
    void add() throws Exception {
        mockMvc.perform(post(API_FACULTIES_URL).
                contentType("application/json").
                content(objectMapper.writeValueAsString(new FacultyForm()))).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllFaculties() throws Exception {
        mockMvc.perform(get(API_FACULTIES_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(API_FACULTIES_URL + "/{faculty-id}", 1)
                .contentType("application/json").param("faculty-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteFaculty() throws Exception {
        mockMvc.perform(delete(API_FACULTIES_URL + "/{faculty-id}", 1)
                .contentType("application/json").param("faculty-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}