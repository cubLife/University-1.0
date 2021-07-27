package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Cathedra;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
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
class CathedraControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
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

    public CathedraControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(CATHEDRAS_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(CATHEDRAS_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Cathedra()));
        this.mockMvc.perform(get(CATHEDRAS_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(CATHEDRAS, (List.of(new Cathedra()))))
                .andExpect(view().name(CATHEDRAS_CATHEDRAS_VIEW));
    }

    @Test
    void showById() throws Exception {
        when(service.findById(1))
                .thenReturn(new Cathedra());
        mockMvc.perform(get(CATHEDRAS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(CATHEDRA, new Cathedra()))
                .andExpect(view().name(CATHEDRAS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        when(service.findById(1))
                .thenReturn(new Cathedra());
        mockMvc.perform(post(CATHEDRAS_CATHEDRA_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(CATHEDRA, new Cathedra()))
                .andExpect(view().name(CATHEDRAS_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(CATHEDRAS_ADD_URL))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was added new cathedra - " + new Cathedra()))
                .andExpect(redirectedUrl(REDIRECT));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(CATHEDRAS_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was deleted cathedra with id - " + 1))
                .andExpect(redirectedUrl(REDIRECT));
    }
}