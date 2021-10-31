package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Subject;
import com.gmail.sergick6690.universityModels.Teacher;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
@ActiveProfiles("test")
class SubjectControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private SubjectService service;
    private static final String SUBJECTS_INDEX_URL = "/subjects";
    private static final String SUBJECTS_ALL_URL = "/subjects/all";
    private static final String SUBJECTS_ID_URL = "/subjects/1";
    private static final String SUBJECTS_SUBJECT_URL = "/subjects/subject";
    private static final String SUBJECTS_RELATED_URL = "/subjects/related";
    private static final String SUBJECTS_ADD_URL = "/subjects/add";
    private static final String SUBJECTS_REMOVE_TEACHER_URL = "/subjects/remove/teacher";
    private static final String SUBJECTS_CHANGE_TEACHER_URL = "/subjects/change/teacher";
    private static final String SUBJECTS_ASSIGN_TEACHER_URL = "/subjects/assign/teacher";
    private static final String SUBJECTS_DELETE_URL = "/subjects/delete";
    private static final String SUBJECTS_INDEX_VIEW = "subjects/index";
    private static final String SUBJECTS_SUBJECTS_VIEW = "subjects/subjects";
    private static final String SUBJECTS_SHOW_VIEW = "subjects/show";
    private static final String SUBJECTS_RELATED_VIEW = "subjects/related";
    private static final String REDIRECT = "/subjects";
    private static final String SUBJECT = "subject";
    private static final String SUBJECTS = "subjects";
    private static final String SUBJECT_ID = "subjectId";
    private static final String TEACHER_ID = "teacherId";
    private static final String MESSAGE = "message";
    private static final String VALUE = "1";

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
        Subject subject = new Subject(1, "Test", new Teacher(), "Test");
        when(service.findById(1))
                .thenReturn(subject);
        mockMvc.perform(get(SUBJECTS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(SUBJECT, subject))
                .andExpect(view().name(SUBJECTS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        Subject subject = new Subject(1, "Test", new Teacher(), "Test");
        when(service.findById(1))
                .thenReturn(subject);
        mockMvc.perform(post(SUBJECTS_SUBJECT_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(SUBJECT, subject))
                .andExpect(view().name(SUBJECTS_SHOW_VIEW));
    }

    @Test
    void showSubjectsRelatedToAudience() throws Exception {
        Subject subject = new Subject(1, "Test", new Teacher(), "Test");
        when(service.findAllSubjectRelatedToAudience(1))
                .thenReturn(List.of(subject));
        mockMvc.perform(post(SUBJECTS_RELATED_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(SUBJECTS, List.of(subject)))
                .andExpect(model().attribute("number", 0))
                .andExpect(view().name(SUBJECTS_RELATED_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(SUBJECTS_ADD_URL))
                .andDo(print());
    }

    @Test
    void removeTeacher() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(SUBJECTS_REMOVE_TEACHER_URL).param(SUBJECT_ID, VALUE))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was removed new teacher from subject with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void assignTeacher() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(SUBJECT_ID, VALUE);
        params.add(TEACHER_ID, VALUE);
        mockMvc.perform(post(SUBJECTS_ASSIGN_TEACHER_URL).params(params))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was assigned teacher with id - " + VALUE + " for subject with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void changeTeacher() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(SUBJECT_ID, VALUE);
        params.add(TEACHER_ID, VALUE);
        mockMvc.perform(post(SUBJECTS_CHANGE_TEACHER_URL).params(params))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was changed teacher on teacher with id - " + VALUE + " for subject with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(SUBJECTS_DELETE_URL).param("id", VALUE))
                .andDo(print())
                .andExpect(redirectedUrl(REDIRECT));
    }
}