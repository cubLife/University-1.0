package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.StudentForm;
import com.gmail.sergick6690.service.*;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.*;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class StudentRestControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private StudentService mockStudentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    FacultyService facultyService;
    @Autowired
    private CathedraService cathedraService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    private static final String API_STUDENTS_URL = "/api/students";
    private static final String API_STUDENTS_LIST_URL = "/api/students/list";
    private static final String API_STUDENTS_ASSIGN_GROUP_URL = "/api/students/assign/group";
    private static final String API_STUDENTS_REMOVE_GROUP_URL = "/api/students/remove/group";
    private static final String API_STUDENTS_CHANGE_GROUP_URL = "/api/students/change/group";
    private static final String API_STUDENTS_ASSIGN_COURSE_URL = "/api/students/assign/course";
    private static final String API_STUDENTS_CHANGE_COURSE_URL = "/api/students/change/course";
    private static final String API_STUDENTS_REMOVE_COURSE_URL = "/api/students/remove/course";
    private static final String STUDENT_ID = "student-id";
    private static final String GROUP_ID = "group-id";
    private static final String COURSE = "course";
    private static final String VALUE = "1";
    private static final String TEST = "Test";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void add() throws Exception {
        createTestData();
        mockMvc.perform(post(API_STUDENTS_URL).
                content(objectMapper.writeValueAsString(new StudentForm(TEST, TEST, TEST, 1, 1, 1))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllStudents() throws Exception {
        mockMvc.perform(get(API_STUDENTS_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(API_STUDENTS_URL).param("studentI-id", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(mockStudentService).add(new Student());
        mockMvc.perform(MockMvcRequestBuilders.delete(API_STUDENTS_URL + "/{student-id}", 1)
                .contentType("application/json").param("student-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void assignGroup() throws Exception {
        createTestData();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(GROUP_ID, VALUE);
        mockMvc.perform(put(API_STUDENTS_ASSIGN_GROUP_URL).params(params)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeFromGroup() throws Exception {
        createTestData();
        mockMvc.perform(put(API_STUDENTS_REMOVE_GROUP_URL).param(STUDENT_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changeGroup() throws Exception {
        createTestData();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(GROUP_ID, VALUE);
        mockMvc.perform(put(API_STUDENTS_CHANGE_GROUP_URL).params(params)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void assignCourse() throws Exception {
        createTestData();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(COURSE, VALUE);
        mockMvc.perform(put(API_STUDENTS_ASSIGN_COURSE_URL).params(params)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeFromCourse() throws Exception {
        createTestData();
        mockMvc.perform(put(API_STUDENTS_REMOVE_COURSE_URL).param(STUDENT_ID, VALUE)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void changeCourse() throws Exception {
        createTestData();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(STUDENT_ID, VALUE);
        params.add(COURSE, VALUE);
        mockMvc.perform(put(API_STUDENTS_CHANGE_COURSE_URL).params(params)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void createTestData() throws ServiceException {
        scheduleService.add(new Schedule(TEST + 1));
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyService.add(faculty);
        cathedraService.add(new Cathedra(TEST, facultyService.findById(1)));
        groupService.add(new Group(TEST + 1, scheduleService.findById(1), cathedraService.findById(1)));
        groupService.add(new Group(TEST + 1, scheduleService.findById(1), cathedraService.findById(1)));
        studentService.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(1).group(groupService.findById(1)).course(1).build());
    }
}