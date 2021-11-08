package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.TeacherForm;
import com.gmail.sergick6690.service.TeacherService;
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
@ContextConfiguration(classes = {TeacherRestController.class})
@ActiveProfiles("test")
class TeacherRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeacherService teacherService;
    @Autowired
    ObjectMapper objectMapper;
    private static final String TEST = "Test";
    private static final String API_TEACHERS_URL = "/api/teachers";
    private static final String API_TEACHERS_LIST_URL = "/api/teachers/list";
    private static final String API_TEACHERS_ASSIGN_SCHEDULE_URL = "/api/teachers/assign/schedule";
    private static final String API_TEACHERS_REMOVE_SCHEDULE_URL = "/api/teachers/remove/schedule";
    private static final String API_TEACHERS_CHANGE_SCHEDULE_URL = "/api/teachers/change/schedule";
    private static final String TEACHER_ID = "teacher-id";
    private static final String SCHEDULE_ID = "schedule-id";
    private static final String VALUE = "1";

    @Test
    void addTeacher() throws Exception {
        mockMvc.perform(post(API_TEACHERS_URL)
                .content(objectMapper.writeValueAsString(new TeacherForm(TEST, TEST, TEST, 1, TEST, 1)))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void showAllTeachers() throws Exception {
        mockMvc.perform(get(API_TEACHERS_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showTeachersById() throws Exception {
        mockMvc.perform(get(API_TEACHERS_URL + "/{teacher-id}", 1)
                .param(TEACHER_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void assignSchedule() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(TEACHER_ID, VALUE);
        params.add(SCHEDULE_ID, VALUE);
        mockMvc.perform(put(API_TEACHERS_ASSIGN_SCHEDULE_URL).params(params)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeSchedule() throws Exception {
        mockMvc.perform(put(API_TEACHERS_REMOVE_SCHEDULE_URL).param(TEACHER_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changeSchedule() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(TEACHER_ID, VALUE);
        params.add(SCHEDULE_ID, VALUE);
        mockMvc.perform(put(API_TEACHERS_CHANGE_SCHEDULE_URL).params(params)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeTeacher() throws Exception {
        mockMvc.perform(delete(API_TEACHERS_URL + "/{teacherId}", 1).param(TEACHER_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}