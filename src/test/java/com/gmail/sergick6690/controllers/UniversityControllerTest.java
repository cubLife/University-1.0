package com.gmail.sergick6690.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UniversityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String UNIVERSITY_URL = "http://localhost:8080";
    private static final String UNIVERSITY_VIEW = "university";

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(UNIVERSITY_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(UNIVERSITY_VIEW));
    }
}
