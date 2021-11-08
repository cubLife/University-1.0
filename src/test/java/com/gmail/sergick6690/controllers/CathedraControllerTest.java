package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.universityModels.Cathedra;
import com.gmail.sergick6690.universityModels.Faculty;
import com.gmail.sergick6690.universityModels.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {CathedraController.class})
@ActiveProfiles("test")
class CathedraControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    CathedraService service;
    private static final String CATHEDRAS_INDEX_URL = "/cathedras";
    private static final String CATHEDRAS_ALL_URL = "/cathedras/all";
    private static final String CATHEDRAS_ID_URL = "/cathedras/1";
    private static final String CATHEDRAS_CATHEDRA_URL = "/cathedras/cathedra";
    private static final String CATHEDRAS_ADD_URL = "/cathedras/add";
    private static final String CATHEDRAS_DELETE_URL = "/cathedras/delete";
    private static final String CATHEDRAS_INDEX_VIEW = "cathedra/index";
    private static final String CATHEDRAS_CATHEDRAS_VIEW = "cathedra/cathedras";
    private static final String CATHEDRAS_SHOW_VIEW = "cathedra/show";
    private static final String CATHEDRA = "cathedra";
    private static final String CATHEDRAS = "cathedras";
    private static final String REDIRECT = "/cathedras";

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(CATHEDRAS_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(CATHEDRAS_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        Cathedra cathedra = new Cathedra(1, "Test", new Faculty(), new ArrayList<Group>());
        when(service.findAll())
                .thenReturn(List.of(cathedra));
        this.mockMvc.perform(get(CATHEDRAS_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(CATHEDRAS, (List.of(cathedra))))
                .andExpect(view().name(CATHEDRAS_CATHEDRAS_VIEW));
    }

    @Test
    void showById() throws Exception {
        Cathedra cathedra = new Cathedra(1, "Test", new Faculty(), new ArrayList<Group>());
        when(service.findById(1))
                .thenReturn(cathedra);
        mockMvc.perform(get(CATHEDRAS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(CATHEDRA, cathedra))
                .andExpect(view().name(CATHEDRAS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        Cathedra cathedra = new Cathedra(1, "Test", new Faculty(), new ArrayList<Group>());
        when(service.findById(1))
                .thenReturn(cathedra);
        mockMvc.perform(post(CATHEDRAS_CATHEDRA_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(CATHEDRA, cathedra))
                .andExpect(view().name(CATHEDRAS_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(CATHEDRAS_ADD_URL))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(CATHEDRAS_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was deleted cathedra with id - " + 1))
                .andExpect(redirectedUrl(REDIRECT));
    }
}