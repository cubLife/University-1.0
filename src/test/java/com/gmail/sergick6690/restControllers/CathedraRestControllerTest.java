package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.CathedraForm;
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
class CathedraRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;

    private static final String DEV_CATHEDRAS_URL = "/dev/cathedras";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void add() throws Exception {
        facultyService.add(new Faculty("Test"));
        mockMvc.perform(post(DEV_CATHEDRAS_URL).
                content(objectMapper.writeValueAsString(new CathedraForm("Test", 1))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllCathedras() throws Exception {
        mockMvc.perform(get(DEV_CATHEDRAS_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(DEV_CATHEDRAS_URL)
                .contentType("application/json").param("cathedraId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteCatrhedra() throws Exception {
        mockMvc.perform(delete(DEV_CATHEDRAS_URL + "/{cathedraId}", 1)
                .contentType("application/json").param("cathedraId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}