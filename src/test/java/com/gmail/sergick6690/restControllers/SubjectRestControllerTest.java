package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.SubjectForm;
import com.gmail.sergick6690.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SubjectRestController.class})
@ActiveProfiles("test")
class SubjectRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SubjectService subjectService;
    @Autowired
    ObjectMapper objectMapper;
    private static final String API_SUBJECTS_URL = "/api/subjects";
    private static final String API_SUBJECTS_LIST_URL = "/api/subjects/list";
    private static final String API_SUBJECTS_ASSIGN_TEACHER_URL = "/api/subjects/assign/teacher";
    private static final String API_SUBJECTS_REMOVE_TEACHER_URL = "/api/subjects/remove/teacher";
    private static final String API_SUBJECTS_CHANGE_TEACHER_URL = "/api/subjects/change/teacher";
    private static final String API_SUBJECTS_RELATED_TO_AUDIENCE_URL = "/api/subjects/related";
    private static final String SUBJECT_ID = "subject-id";
    private static final String TEACHER_ID = "teacher-id";
    private static final String VALUE = "1";
    private static final String TEST = "Test";

    @Test
    void addSubject() throws Exception {
        mockMvc.perform(post(API_SUBJECTS_URL).
                content(objectMapper.writeValueAsString(new SubjectForm(TEST, 1, TEST))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllSubjects() throws Exception {
        mockMvc.perform(get(API_SUBJECTS_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showSubjectById() throws Exception {
        mockMvc.perform(get(API_SUBJECTS_URL + "/{subject-id}", 1)
                .contentType("application/json").param(SUBJECT_ID, VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void assignTeacher() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(SUBJECT_ID, VALUE);
        params.add(TEACHER_ID, VALUE);
        mockMvc.perform(put(API_SUBJECTS_ASSIGN_TEACHER_URL)
                .contentType("application/json").params(params))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeTeacher() throws Exception {
        mockMvc.perform(put(API_SUBJECTS_REMOVE_TEACHER_URL)
                .contentType("application/json").param(SUBJECT_ID, VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changeTeacher() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(SUBJECT_ID, VALUE);
        params.add(TEACHER_ID, VALUE);
        mockMvc.perform(put(API_SUBJECTS_CHANGE_TEACHER_URL)
                .contentType("application/json").params(params))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showSubjectsRelatedToAudience() throws Exception {
        mockMvc.perform(get(API_SUBJECTS_RELATED_TO_AUDIENCE_URL + "/{audience-id}", 1)
                .queryParam(SUBJECT_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeSubject() throws Exception {
        mockMvc.perform(delete(API_SUBJECTS_URL + "/{subject-id}", 1)
                .param(SUBJECT_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}