package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Subject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
class SubjectControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    @MockBean
    private SubjectService service;
    private static final String SUBJECTS_INDEX_URL = "/subjects/index";
    private static final String SUBJECTS_ALL_URL = "/subjects/all";
    private static final String SUBJECTS_ID_URL = "/subjects/1";
    private static final String SUBJECTS_SUBJECT_URL = "/subjects/subject";
    private static final String SUBJECTS_RELATED_URL = "/subjects/related";
    private static final String SUBJECTS_INDEX_VIEW = "subjects/index";
    private static final String SUBJECTS_SUBJECTS_VIEW = "subjects/subjects";
    private static final String SUBJECTS_SHOW_VIEW = "subjects/show";
    private static final String SUBJECTS_RELATED_VIEW = "subjects/related";
    private static final String SUBJECT = "subject";
    private static final String SUBJECTS = "subjects";

    public SubjectControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeAll
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(SUBJECTS_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(SUBJECTS_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Subject()));
        this.mockMvc.perform(get(SUBJECTS_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(SUBJECTS, (List.of(new Subject()))))
                .andExpect(view().name(SUBJECTS_SUBJECTS_VIEW));
    }

    @Test
    void showById() throws Exception {
        when(service.findById(1))
                .thenReturn(new Subject());
        mockMvc.perform(get(SUBJECTS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(SUBJECT, new Subject()))
                .andExpect(view().name(SUBJECTS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(service.findById(1))
                .thenReturn(new Subject());
        mockMvc.perform(post(SUBJECTS_SUBJECT_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(SUBJECT, new Subject()))
                .andExpect(view().name(SUBJECTS_SHOW_VIEW));
    }

    @Test
    void showSubjectsRelatedToAudience() throws Exception {
        when(service.findAllSubjectRelatedToAudience(1))
                .thenReturn(List.of(new Subject()));
        mockMvc.perform(post(SUBJECTS_RELATED_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(SUBJECTS, List.of(new Subject())))
                .andExpect(model().attribute("number", 0))
                .andExpect(view().name(SUBJECTS_RELATED_VIEW));
    }
}