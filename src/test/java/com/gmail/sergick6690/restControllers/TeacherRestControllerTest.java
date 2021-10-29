package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.TeacherForm;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Schedule;
import com.gmail.sergick6690.universityModels.Teacher;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class TeacherRestControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private TeacherService mockTeacherService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private WebApplicationContext webApplicationContext;
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

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void addTeacher() throws Exception {
        generateTestData();
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
        mockMvc.perform(get(API_TEACHERS_URL).param(TEACHER_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void assignSchedule() throws Exception {
        generateTestData();
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
        generateTestData();
        mockMvc.perform(put(API_TEACHERS_REMOVE_SCHEDULE_URL).param(TEACHER_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changeSchedule() throws Exception {
        generateTestData();
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
        doNothing().when(mockTeacherService).add(new Teacher());
        mockMvc.perform(delete(API_TEACHERS_URL + "/{teacherId}", 1).param(TEACHER_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void generateTestData() throws ServiceException {
        scheduleService.add(new Schedule(TEST + 1));
        scheduleService.add(new Schedule(TEST + 2));
        for (int i = 0; i < 5; i++) {
            teacherService.add(Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST)
                    .subjects(null).build());
            teacherService.add(Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST + 1).subjects(null).build());
        }
    }
}