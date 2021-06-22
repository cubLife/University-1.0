package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Teacher;
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
class TeacherControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    @MockBean
    private TeacherService service;
    private static final String TEACHERS_INDEX_URL = "/teachers/index";
    private static final String TEACHERS_ALL_URL = "/teachers/all";
    private static final String TEACHERS_ID_URL = "/teachers/1";
    private static final String TEACHERS_TEACHER_URL = "/teachers/teacher";
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

    @Autowired
    public TeacherControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

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
        when(service.findById(1))
                .thenReturn(new Teacher());
        mockMvc.perform(get(TEACHERS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(TEACHER, new Teacher()))
                .andExpect(view().name(TEACHERS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(service.findById(1))
                .thenReturn(new Teacher());
        mockMvc.perform(post(TEACHERS_TEACHER_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(TEACHER, new Teacher()))
                .andExpect(view().name(TEACHERS_SHOW_VIEW));
    }

    @Test
    void showWithEqualDegree() throws Exception {
        when(service.findTeachersCountWithEqualDegree(DEGREE_NAME))
                .thenReturn(1);
        mockMvc.perform(post(TEACHERS_EQUAL_DEGREE_URL).param(DEGREE, DEGREE_NAME))
                .andDo(print())
                .andExpect(model().attribute(COUNT, 1))
                .andExpect(model().attribute(DEGREE, DEGREE_NAME))
                .andExpect(view().name(TEACHERS_EQUAL_DEGREE_VIEW));
    }
}