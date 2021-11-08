package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.universityModels.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {FacultyController.class})
@ActiveProfiles("test")
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyService service;
    private static final String FACULTIES_INDEX_URL = "/faculties";
    private static final String FACULTIES_ALL_URL = "/faculties/all";
    private static final String FACULTIES_ID_URL = "/faculties/1";
    private static final String FACULTIES_FACULTY_URL = "/faculties/faculty";
    private static final String FACULTIES_ADD_URL = "/faculties/add";
    private static final String FACULTIES_DELETE_URL = "/faculties/delete";
    private static final String FACULTIES_INDEX_VIEW = "faculties/index";
    private static final String FACULTIES_FACULTIES_VIEW = "faculties/faculties";
    private static final String FACULTIES_SHOW_VIEW = "faculties/show";
    private static final String FACULTY = "faculty";
    private static final String FACULTIES = "faculties";
    private static final String REDIRECT = "/faculties";

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(FACULTIES_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(FACULTIES_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Faculty()));
        this.mockMvc.perform(get(FACULTIES_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(FACULTIES, (List.of(new Faculty()))))
                .andExpect(view().name(FACULTIES_FACULTIES_VIEW));
    }

    @Test
    void showById() throws Exception {
        when(service.findById(1))
                .thenReturn(new Faculty());
        mockMvc.perform(get(FACULTIES_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(FACULTY, new Faculty()))
                .andExpect(view().name(FACULTIES_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(service.findById(1))
                .thenReturn(new Faculty());
        mockMvc.perform(post(FACULTIES_FACULTY_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(FACULTY, new Faculty()))
                .andExpect(view().name(FACULTIES_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(FACULTIES_ADD_URL))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(FACULTIES_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was deleted faculty with id - " + 1))
                .andExpect(redirectedUrl(REDIRECT));
    }
}
