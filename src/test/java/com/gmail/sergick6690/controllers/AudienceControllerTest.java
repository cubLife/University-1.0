package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Audience;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
class AudienceControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
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

    @Autowired
    public AudienceControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

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
                .andExpect(model().attribute("audience", new Audience()))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(AUDIENCES_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(redirectedUrl(REDIRECT));
    }
}