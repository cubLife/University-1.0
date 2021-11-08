package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.ScheduleForm;
import com.gmail.sergick6690.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ScheduleRestController.class})
@ActiveProfiles("test")
class ScheduleRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ScheduleService scheduleService;
    @Autowired
    ObjectMapper objectMapper;
    private static final String API_SCHEDULES_URL = "/api/schedules";
    private static final String API_SCHEDULES_LIST_URL = "/api/schedules/list";

    @Test
    void addSchedule() throws Exception {
        mockMvc.perform(post(API_SCHEDULES_URL).
                content(objectMapper.writeValueAsString(new ScheduleForm())).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllSchedules() throws Exception {
        mockMvc.perform(get(API_SCHEDULES_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showScheduleById() throws Exception {
        mockMvc.perform(get(API_SCHEDULES_URL + "/{schedule-id}", 1)
                .contentType("application/json").param("schedule-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteScheduleById() throws Exception {
        mockMvc.perform(delete(API_SCHEDULES_URL + "/{schedule-id}", 1)
                .contentType("application/json").param("schedule-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}