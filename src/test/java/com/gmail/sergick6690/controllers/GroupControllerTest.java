package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
class GroupControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    @MockBean
    private GroupService service;
    private static final String GROUPS_INDEX_URL = "/groups";
    private static final String GROUPS_ALL_URL = "/groups/all";
    private static final String GROUPS_ID_URL = "/groups/1";
    private static final String GROUPS_FACULTY_URL = "/groups/group";
    private static final String GROUPS_RELATED_URL = "/groups/related";
    private static final String GROUPS_ADD_URL = "/groups/add";
    private static final String GROUPS_DELETE_URL = "/groups/delete";
    private static final String GROUPS_INDEX_VIEW = "groups/index";
    private static final String GROUPS_GROUPS_VIEW = "groups/groups";
    private static final String GROUPS_SHOW_VIEW = "groups/show";
    private static final String GROUPS_RELATED_VIEW = "groups/related";
    private static final String REDIRECT = "/groups";
    private static final String GROUP = "group";
    private static final String GROUPS = "groups";

    @Autowired
    public GroupControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeAll
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(GROUPS_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(GROUPS_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Group()));
        this.mockMvc.perform(get(GROUPS_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(GROUPS, (List.of(new Group()))))
                .andExpect(view().name(GROUPS_GROUPS_VIEW));
    }

    @Test
    void showById() throws Exception {
        when(service.findById(1))
                .thenReturn(new Group());
        mockMvc.perform(get(GROUPS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(GROUP, new Group()))
                .andExpect(view().name(GROUPS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(service.findById(1))
                .thenReturn(new Group());
        mockMvc.perform(post(GROUPS_FACULTY_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(GROUP, new Group()))
                .andExpect(view().name(GROUPS_SHOW_VIEW));
    }

    @Test
    void showGroupsRelatedToCathedra() throws Exception {
        when(service.findAllGroupsRelatedToCathedra(1))
                .thenReturn(List.of(new Group()));
        mockMvc.perform(post(GROUPS_RELATED_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(GROUPS, List.of(new Group())))
                .andExpect(view().name(GROUPS_RELATED_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(GROUPS_ADD_URL))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was added new group - " + new Group()))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(GROUPS_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was deleted group with id - " + 1))
                .andExpect(redirectedUrl(REDIRECT));
    }
}