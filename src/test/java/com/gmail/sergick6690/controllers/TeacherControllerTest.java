package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Subject;
import com.gmail.sergick6690.university.Teacher;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class TeacherControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private TeacherService service;
    private static final String TEACHERS_INDEX_URL = "/teachers";
    private static final String TEACHERS_ALL_URL = "/teachers/all";
    private static final String TEACHERS_ID_URL = "/teachers/1";
    private static final String TEACHERS_TEACHER_URL = "/teachers/teacher";
    private static final String TEACHERS_ADD_URL = "/teachers/add";
    private static final String TEACHERS_REMOVE_SCHEDULE_URL = "/teachers/remove/schedule";
    private static final String TEACHERS_ASSIGN_SCHEDULE_URL = "/teachers/assign/schedule";
    private static final String TEACHERS_CHANGE_SCHEDULE_URL = "/teachers/change/schedule";
    private static final String TEACHERS_DELETE_TEACHER_URL = "/teachers/delete";
    private static final String TEACHERS_EQUAL_DEGREE_URL = "/teachers/equal/degree";
    private static final String TEACHERS_INDEX_VIEW = "teacher/index";
    private static final String TEACHERS_TEACHERS_VIEW = "teacher/teachers";
    private static final String TEACHERS_SHOW_VIEW = "teacher/show";
    private static final String TEACHERS_EQUAL_DEGREE_VIEW = "teacher/equalDegree";
    private static final String TEACHER = "teacher";
    private static final String TEACHERS = "teachers";
    private static final String DEGREE = "degree";
    private static final String DEGREE_NAME = "Test";
    private static final String COUNT = "count";
    private static final String REDIRECT = "/teachers";
    private static final String TEACHER_ID = "teacherId";
    private static final String SCHEDULE_ID = "scheduleId";
    private static final String MESSAGE = "message";
    private static final String VALUE = "1";

    @BeforeAll
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(TEACHERS_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(TEACHERS_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Teacher()));
        this.mockMvc.perform(get(TEACHERS_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(TEACHERS, (List.of(new Teacher()))))
                .andExpect(view().name(TEACHERS_TEACHERS_VIEW));
    }

    @Test
    void showById() throws Exception {
        Teacher teacher = Teacher.builder().id(1).firstName("Test").lastName("Test").age(0).sex("Test").degree("Test")
                .schedule(new Schedule()).build();
        when(service.findById(1))
                .thenReturn(teacher);
        mockMvc.perform(get(TEACHERS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(TEACHER, teacher))
                .andExpect(view().name(TEACHERS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        Teacher teacher = Teacher.builder().id(1).firstName("Test").lastName("Test").age(0).sex("Test").degree("Test")
                .schedule(new Schedule()).build();
        when(service.findById(1))
                .thenReturn(teacher);
        mockMvc.perform(post(TEACHERS_TEACHER_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(TEACHER, teacher))
                .andExpect(view().name(TEACHERS_SHOW_VIEW));
    }

    @Test
    void showWithEqualDegree() throws Exception {
        when(service.findTeachersCountWithEqualDegree(DEGREE_NAME))
                .thenReturn(1L);
        mockMvc.perform(post(TEACHERS_EQUAL_DEGREE_URL).param(DEGREE, DEGREE_NAME))
                .andDo(print())
                .andExpect(model().attribute(COUNT, 1L))
                .andExpect(model().attribute(DEGREE, DEGREE_NAME))
                .andExpect(view().name(TEACHERS_EQUAL_DEGREE_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(TEACHERS_ADD_URL))
                .andDo(print());
    }

    @Test
    void removeSchedule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(TEACHERS_REMOVE_SCHEDULE_URL).param(TEACHER_ID, VALUE))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was removed schedule for teacher with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void assignSchedule() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(TEACHER_ID, "1");
        params.add(SCHEDULE_ID, "1");
        mockMvc.perform(post(TEACHERS_ASSIGN_SCHEDULE_URL).params(params))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was assigned schedule with id - " + VALUE + " for teacher with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void changeSchedule() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(TEACHER_ID, VALUE);
        params.add(SCHEDULE_ID, VALUE);
        mockMvc.perform(post(TEACHERS_CHANGE_SCHEDULE_URL).params(params))
                .andDo(print())
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(TEACHERS_DELETE_TEACHER_URL).param("id", VALUE))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was deleted student with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }
}