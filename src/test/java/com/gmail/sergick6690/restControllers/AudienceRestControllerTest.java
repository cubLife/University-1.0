package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Audience;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class AudienceRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AudienceService audienceService;
    private static final String DEV_AUDIENCES_URL = "/dev/audiences";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void addAudience() throws Exception {
        mockMvc.perform(post(DEV_AUDIENCES_URL).
                content(objectMapper.writeValueAsString(new Audience())).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());

    }

    @Test
    void getAudience() throws Exception {
        when(audienceService.findById(1))
                .thenReturn(new Audience(1, 1));
        mockMvc.perform(get(DEV_AUDIENCES_URL)
                .contentType("application/json").param("audienceId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void showAllAudience() throws Exception {
        mockMvc.perform(get(DEV_AUDIENCES_URL)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteAudience() throws Exception {
        mockMvc.perform(delete(DEV_AUDIENCES_URL + "/{audienceId}", 1)
                .contentType("application/json").param("audienceId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}