package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.GroupForm;
import com.gmail.sergick6690.service.GroupService;
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
@ContextConfiguration(classes = {GroupRestController.class})
@ActiveProfiles("test")
class GroupRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GroupService groupService;
    @Autowired
    ObjectMapper objectMapper;
    private static final String TEST = "TEST";
    private static final String API_GROUPS_URL = "/api/groups";
    private static final String API_GROUPS_RELATED_URL = "/api/groups/related";
    private static final String API_GROUPS_LIST_URL = "/api/groups/list";

    @Test
    void addGroup() throws Exception {
        mockMvc.perform(post(API_GROUPS_URL).
                content(objectMapper.writeValueAsString(new GroupForm(TEST + 1, 1, 1))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllGroups() throws Exception {
        mockMvc.perform(get(API_GROUPS_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(API_GROUPS_URL + "/{group-id}", 1)
                .contentType("application/json").param("group-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showGroupsRelatedToCathedra() throws Exception {
        mockMvc.perform(get(API_GROUPS_RELATED_URL + "/{cathedra-id}", 1)
                .contentType("application/json")
                .param("cathedra-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(API_GROUPS_URL + "/{group-id}", 1)
                .contentType("application/json").param("group-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
