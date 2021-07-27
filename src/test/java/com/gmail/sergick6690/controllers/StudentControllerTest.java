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
class StudentControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    @MockBean
    private StudentService service;
    private static final String STUDENTS_INDEX_URL = "/students";
    private static final String STUDENTS_ALL_URL = "/students/all";
    private static final String STUDENTS_ID_URL = "/students/1";
    private static final String STUDENTS_ADD_URL = "/students/add";
    private static final String STUDENTS_DELETE_URL = "/students/delete";
    private static final String STUDENTS_STUDENT_URL = "/students/student";
    private static final String STUDENTS_ASSIGN_GROUP_URL = "/students/assign/group";
    private static final String STUDENTS_REMOVE_GROUP_URL = "/students/remove/group";
    private static final String STUDENTS_CHANGE_GROUP_URL = "/students/change/group";
    private static final String STUDENTS_ASSIGN_COURSE_URL = "/students/assign/course";
    private static final String STUDENTS_REMOVE_COURSE_URL = "/students/remove/course";
    private static final String STUDENTS_INDEX_VIEW = "students/index";
    private static final String STUDENTS_STUDENTS_VIEW = "students/students";
    private static final String STUDENTS_SHOW_VIEW = "students/show";
    private static final String REDIRECT = "/students";
    private static final String STUDENT = "student";
    private static final String STUDENTS = "students";
    private static final String GROUP_ID = "groupId";
    private static final String STUDENT_ID = "studentId";
    private static final String COURSE = "course";
    private static final String VALUE = "1";
    private static final String MESSAGE = "message";

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
        mockMvc.perform(post(STUDENTS_STUDENT_URL).param("id", VALUE))
                .andDo(print())
                .andExpect(model().attribute(STUDENT, new Student()))
                .andExpect(view().name(STUDENTS_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(STUDENTS_ADD_URL))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was added new student - " + new Student()))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(STUDENTS_DELETE_URL).param("id", VALUE))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was deleted student with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void assignGroup() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(GROUP_ID, VALUE);
        mockMvc.perform(post(STUDENTS_ASSIGN_GROUP_URL).params(params))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was assigned group with id - " + VALUE + " for student with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void removeFromGroup() throws Exception {
        mockMvc.perform(post(STUDENTS_REMOVE_GROUP_URL).param(STUDENT_ID, VALUE))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was deleted student with id - 1 from group"))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void changeGroup() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(GROUP_ID, VALUE);
        mockMvc.perform(post(STUDENTS_CHANGE_GROUP_URL).params(params))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was changed group on group with id - " + VALUE + " for student with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void assignCourse() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(COURSE, VALUE);
        mockMvc.perform(post(STUDENTS_ASSIGN_COURSE_URL).params(params))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was assigned course - " + VALUE + " for student with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));

    }

    @Test
    void removeFromCourse() throws Exception {
        mockMvc.perform(post(STUDENTS_REMOVE_COURSE_URL).param(STUDENT_ID, VALUE))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was removed course for student with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void changeCourse() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(COURSE, VALUE);
        mockMvc.perform(post(STUDENTS_ASSIGN_COURSE_URL).params(params))
                .andDo(print())
                .andExpect(flash().attribute(MESSAGE, "Was assigned course - " + VALUE + " for student with id - " + VALUE))
                .andExpect(redirectedUrl(REDIRECT));

    }
}