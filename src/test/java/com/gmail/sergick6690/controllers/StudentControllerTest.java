package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.universityModels.Group;
import com.gmail.sergick6690.universityModels.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {StudentController.class})
@ActiveProfiles("test")
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
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
        Student student = Student.builder().group(new Group()).build();
        when(service.findById(1))
                .thenReturn(student);
        mockMvc.perform(get(STUDENTS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(STUDENT, student))
                .andExpect(view().name(STUDENTS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        Student student = Student.builder().group(new Group()).build();
        when(service.findById(1))
                .thenReturn(student);
        mockMvc.perform(post(STUDENTS_STUDENT_URL).param("id", VALUE))
                .andDo(print())
                .andExpect(model().attribute(STUDENT, student))
                .andExpect(view().name(STUDENTS_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(STUDENTS_ADD_URL))
                .andDo(print());
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