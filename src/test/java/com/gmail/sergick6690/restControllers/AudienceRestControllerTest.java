package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.universityModels.Audience;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {AudienceRestController.class})
@ActiveProfiles("test")
class AudienceRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AudienceService audienceService;
    private static final String API_AUDIENCES_URL = "/api/audiences";
    private static final String API_AUDIENCES_LIST_URL = "/api/audiences/list";

    @Test
    void addAudience() throws Exception {
        mockMvc.perform(post(API_AUDIENCES_URL).
                content(objectMapper.writeValueAsString(new Audience())).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void getAudience() throws Exception {
        when(audienceService.findById(1))
                .thenReturn(new Audience(1, 1));
        mockMvc.perform(get(API_AUDIENCES_URL + "/{audience-id}", 1)
                .contentType("application/json").param("audience-id", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void showAllAudience() throws Exception {
        mockMvc.perform(get(API_AUDIENCES_LIST_URL)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteAudience() throws Exception {
        mockMvc.perform(delete(API_AUDIENCES_URL + "/{audience-id}", 1)
                .contentType("application/json").param("audience-id", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}