package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.SubjectForm;
import com.gmail.sergick6690.service.*;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class SubjectRestControllerTest {
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
    private ItemService itemService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    private static final String DEV_SUBJECTS_URL = "/dev/subjects";
    private static final String DEV_SUBJECTS_ASSIGN_TEACHER_URL = "/dev/subjects/assign/teacher";
    private static final String DEV_SUBJECTS_REMOVE_TEACHER_URL = "/dev/subjects/remove/teacher";
    private static final String DEV_SUBJECTS_CHANGE_TEACHER_URL = "/dev/subjects/change/teacher";
    private static final String DEV_SUBJECTS_RELATED_TO_AUDIENCE_URL = "/dev/subjects/related";
    private static final String SUBJECT_ID = "subjectId";
    private static final String TEACHER_ID = "teacherId";
    private static final String VALUE = "1";
    private static final String TEST = "Test";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void addSubject() throws Exception {
        generateTestData();
        mockMvc.perform(post(DEV_SUBJECTS_URL).
                content(objectMapper.writeValueAsString(new SubjectForm(TEST, 1, TEST))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllSubjects() throws Exception {
        mockMvc.perform(get(DEV_SUBJECTS_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showSubjectById() throws Exception {
        mockMvc.perform(get(DEV_SUBJECTS_URL)
                .contentType("application/json").param(SUBJECT_ID, VALUE))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void assignTeacher() throws Exception {
        generateTestData();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(SUBJECT_ID, VALUE);
        params.add(TEACHER_ID, VALUE);
        mockMvc.perform(put(DEV_SUBJECTS_ASSIGN_TEACHER_URL)
                .contentType("application/json").params(params))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeTeacher() throws Exception {
        generateTestData();
        mockMvc.perform(put(DEV_SUBJECTS_REMOVE_TEACHER_URL)
                .contentType("application/json").param(SUBJECT_ID, VALUE ))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changeTeacher() throws Exception {
        generateTestData();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(SUBJECT_ID, VALUE);
        params.add(TEACHER_ID, VALUE);
        mockMvc.perform(put(DEV_SUBJECTS_CHANGE_TEACHER_URL)
                .contentType("application/json").params(params))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showSubjectsRelatedToAudience() throws Exception {
        generateTestData();
        mockMvc.perform(get(DEV_SUBJECTS_RELATED_TO_AUDIENCE_URL).param(SUBJECT_ID, VALUE)
        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeSubject() throws Exception {
        generateTestData();
        mockMvc.perform(delete(DEV_SUBJECTS_URL+"/{subjectId}", 1)
        .param(SUBJECT_ID,VALUE)
        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void generateTestData() throws ServiceException {
        scheduleService.add(new Schedule(TEST + TEST));
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST).build();
        teacherService.add(teacher);
        Audience audience = new Audience();
        audience.setNumber(0);
        Audience audience1 = new Audience();
        audience1.setNumber(1);
        audienceService.add(audience);
        audienceService.add(audience1);
        subjectService.add(new Subject(TEST, teacherService.findById(1), TEST + TEST+TEST));
    }
}