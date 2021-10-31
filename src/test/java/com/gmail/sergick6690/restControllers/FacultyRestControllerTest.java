package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Faculty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class FacultyRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    private static final String API_FACULTIES_URL = "/api/faculties";
    private static final String API_FACULTIES_LIST_URL = "/api/faculties/list";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(API_FACULTIES_URL).
                content(objectMapper.writeValueAsString(new Faculty())).
                contentType("application/json")).
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
        mockMvc.perform(get(API_FACULTIES_URL)
                .contentType("application/json").param("faculty-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteFaculty() throws Exception {
        facultyService.add(new Faculty("Test"));
        mockMvc.perform(delete(API_FACULTIES_URL + "/{faculty-id}", 1)
                .contentType("application/json").param("faculty-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}