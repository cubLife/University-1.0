package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Student;
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
class StudentControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    @MockBean
    private StudentService service;
    private static final String STUDENTS_INDEX_URL = "/students/index";
    private static final String STUDENTS_ALL_URL = "/students/all";
    private static final String STUDENTS_ID_URL = "/students/1";
    private static final String STUDENTS_STUDENT_URL = "/students/student";
    private static final String STUDENTS_INDEX_VIEW = "students/index";
    private static final String STUDENTS_STUDENTS_VIEW = "students/students";
    private static final String STUDENTS_SHOW_VIEW = "students/show";
    private static final String STUDENT = "student";
    private static final String STUDENTS = "students";

    @Autowired
    public StudentControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeAll
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(STUDENTS_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(STUDENTS_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Student()));
        this.mockMvc.perform(get(STUDENTS_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(STUDENTS, (List.of(new Student()))))
                .andExpect(view().name(STUDENTS_STUDENTS_VIEW));
    }

    @Test
    void showById() throws Exception {
        when(service.findById(1))
                .thenReturn(new Student());
        mockMvc.perform(get(STUDENTS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(STUDENT, new Student()))
                .andExpect(view().name(STUDENTS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(service.findById(1))
                .thenReturn(new Student());
        mockMvc.perform(post(STUDENTS_STUDENT_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(STUDENT, new Student()))
                .andExpect(view().name(STUDENTS_SHOW_VIEW));
    }
}