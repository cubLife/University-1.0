package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.GroupForm;
import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Cathedra;
import com.gmail.sergick6690.universityModels.Faculty;
import com.gmail.sergick6690.universityModels.Schedule;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class GroupRestControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;
    @Autowired
    CathedraService cathedraService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    private static final String TEST = "TEST";
    private static final String DEV_GROUPS_URL = "/dev/groups";
    private static final String DEV_GROUPS_RELATED_URL = "/dev/groups/related";

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void addGroup() throws Exception {
        createTestData();
        mockMvc.perform(post(DEV_GROUPS_URL).
                content(objectMapper.writeValueAsString(new GroupForm("Te_st", 1, 1))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllGroups() throws Exception {
        mockMvc.perform(get(DEV_GROUPS_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(DEV_GROUPS_URL)
                .contentType("application/json").param("groupId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showGroupsRelatedToCathedra() throws Exception {
        mockMvc.perform(get(DEV_GROUPS_RELATED_URL + "/{cathedraId}", 1)
                .contentType("application/json")
                .param("cathedraId", "1"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(DEV_GROUPS_URL + "/{groupId}", 1)
                .contentType("application/json").param("groupId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void createTestData() throws ServiceException {
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyService.add(faculty);
        cathedraService.add(new Cathedra(TEST, faculty));
        cathedraService.add(new Cathedra(TEST, faculty));
        scheduleService.add(new Schedule(TEST + TEST));
    }
}
