package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.universityModels.Audience;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {AudienceController.class})
@ActiveProfiles("test")
class AudienceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AudienceService audienceService;
    private static final String AUDIENCES_INDEX_URL = "/audiences";
    private static final String AUDIENCES_ALL_URL = "/audiences/all";
    private static final String AUDIENCES_ID_URL = "/audiences/1";
    private static final String AUDIENCES_AUDIENCE_URL = "/audiences/audience";
    private static final String AUDIENCES_ADD_URL = "/audiences/add";
    private static final String AUDIENCES_DELETE_URL = "/audiences/delete";
    private static final String AUDIENCES_INDEX_VIEW = "audiences/index";
    private static final String AUDIENCES_AUDIENCES_VIEW = "audiences/audiences";
    private static final String AUDIENCES_SHOW_VIEW = "audiences/show";
    private static final String AUDIENCES = "audiences";
    private static final String AUDIENCE = "audience";
    private static final String REDIRECT = "/audiences";

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(AUDIENCES_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(AUDIENCES_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(audienceService.findAll())
                .thenReturn(List.of(new Audience(1, 1)));
        this.mockMvc.perform(get(AUDIENCES_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(AUDIENCES, (List.of(new Audience(1, 1)))))
                .andExpect(view().name(AUDIENCES_AUDIENCES_VIEW));
    }

    @Test
    void showById() throws Exception {
        when(audienceService.findById(1))
                .thenReturn(new Audience(1, 1));
        mockMvc.perform(get(AUDIENCES_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(AUDIENCE, new Audience(1, 1)))
                .andExpect(view().name(AUDIENCES_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(audienceService.findById(1))
                .thenReturn(new Audience(1, 1));
        mockMvc.perform(post(AUDIENCES_AUDIENCE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(AUDIENCE, new Audience(1, 1)))
                .andExpect(view().name(AUDIENCES_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(AUDIENCES_ADD_URL))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(flash().attribute("message", "Was added new audience - null"))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(AUDIENCES_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was deleted audience with id - " + 1))
                .andExpect(redirectedUrl(REDIRECT));
    }
}