package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Schedule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ActiveProfiles("test")
class ScheduleRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ScheduleService scheduleService;
    @Autowired
    ObjectMapper objectMapper;
    private static final String API_SCHEDULES_URL = "/api/schedules";
    private static final String API_SCHEDULES_LIST_URL = "/api/schedules/list";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void addSchedule() throws Exception {
        mockMvc.perform(post(API_SCHEDULES_URL).
                content(objectMapper.writeValueAsString(new Schedule())).
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
        mockMvc.perform(get(API_SCHEDULES_URL)
                .contentType("application/json").param("schedule-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteScheduleById() throws Exception {
        doNothing().when(scheduleService).add(new Schedule());
        mockMvc.perform(delete(API_SCHEDULES_URL + "/{schedule-id}", 1)
                .contentType("application/json").param("schedule-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}