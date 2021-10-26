package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.ItemForm;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Audience;
import com.gmail.sergick6690.universityModels.Schedule;
import com.gmail.sergick6690.universityModels.Subject;
import com.gmail.sergick6690.universityModels.Teacher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class ItemRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private AudienceService audienceService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    private static final String DEV_ITEMS_URL = "/dev/items";
    private static final String TEST = "Test";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void add() throws Exception {
        createTestData();
        mockMvc.perform(post(DEV_ITEMS_URL).
                content(objectMapper.writeValueAsString(new ItemForm(1, "monday", 13, 1, 2, 1))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllSubjects() throws Exception {
        mockMvc.perform(get(DEV_ITEMS_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(DEV_ITEMS_URL)
                .contentType("application/json").param("itemId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(DEV_ITEMS_URL + "/{itemId}", 1)
                .contentType("application/json").param("cathedraId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void createTestData() throws ServiceException {
        Schedule schedule = new Schedule(TEST + 1);
        scheduleService.add(schedule);
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST).build();
        teacherService.add(teacher);
        Audience audience = new Audience();
        Subject subject = new Subject(TEST, teacherService.findById(1), TEST + TEST + TEST);
        subjectService.add(subject);
        audienceService.add(audience);
    }
}