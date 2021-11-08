package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.universityModels.Schedule;
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
@ContextConfiguration(classes = {ScheduleController.class})
@ActiveProfiles("test")
class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ScheduleService service;
    private static final String SCHEDULES_INDEX_URL = "/schedules";
    private static final String SCHEDULES_ALL_URL = "/schedules/all";
    private static final String SCHEDULES_ID_URL = "/schedules/1";
    private static final String SCHEDULES_ADD_URL = "/schedules/add";
    private static final String SCHEDULES_DELETE_URL = "/schedules/delete";
    private static final String SCHEDULES_SCHEDULE_URL = "/schedules/schedule";
    private static final String SCHEDULES_INDEX_VIEW = "schedules/index";
    private static final String SCHEDULES_SCHEDULES_VIEW = "schedules/schedules";
    private static final String SCHEDULES_SHOW_VIEW = "schedules/show";
    private static final String REDIRECT = "/schedules";
    private static final String SCHEDULE = "schedule";
    private static final String SCHEDULES = "schedules";

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(SCHEDULES_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(SCHEDULES_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Schedule()));
        this.mockMvc.perform(get(SCHEDULES_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(SCHEDULES, (List.of(new Schedule()))))
                .andExpect(view().name(SCHEDULES_SCHEDULES_VIEW));
    }

    @Test
    void showById() throws Exception {
        when(service.findById(1))
                .thenReturn(new Schedule());
        mockMvc.perform(get(SCHEDULES_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(SCHEDULE, new Schedule()))
                .andExpect(view().name(SCHEDULES_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(service.findById(1))
                .thenReturn(new Schedule());
        mockMvc.perform(post(SCHEDULES_SCHEDULE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(SCHEDULE, new Schedule()))
                .andExpect(view().name(SCHEDULES_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(SCHEDULES_ADD_URL))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(SCHEDULES_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was deleted schedule with id - " + 1))
                .andExpect(redirectedUrl(REDIRECT));
    }
}