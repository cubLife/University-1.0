package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.service.ItemService;
import com.gmail.sergick6690.universityModels.Audience;
import com.gmail.sergick6690.universityModels.Item;
import com.gmail.sergick6690.universityModels.Schedule;
import com.gmail.sergick6690.universityModels.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {ItemController.class})
@ActiveProfiles("test")
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService service;
    private static final String ITEMS_INDEX_URL = "/items/";
    private static final String ITEMS_ALL_URL = "/items/all";
    private static final String ITEMS_ID_URL = "/items/1";
    private static final String ITEMS_ITEM_URL = "/items/item";
    private static final String ITEMS_ADD_URL = "/items/add";
    private static final String ITEMS_DELETE_URL = "/items/delete";
    private static final String ITEMS_INDEX_VIEW = "items/index";
    private static final String ITEMS_ITEMS_VIEW = "items/items";
    private static final String ITEMS_SHOW_VIEW = "items/show";
    private static final String REDIRECT = "/items";
    private static final String ITEM = "item";
    private static final String ITEMS = "items";

    @Test
    void startPage() throws Exception {
        this.mockMvc.perform(get(ITEMS_INDEX_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(ITEMS_INDEX_VIEW));
    }

    @Test
    void showAll() throws Exception {
        when(service.findAll())
                .thenReturn(List.of(new Item()));
        this.mockMvc.perform(get(ITEMS_ALL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute(ITEMS, (List.of(new Item()))))
                .andExpect(view().name(ITEMS_ITEMS_VIEW));
    }

    @Test
    void showById() throws Exception {
        Item item = new Item(1, new Subject(), null, 0, new Audience(), 0, new Schedule());
        when(service.findById(1))
                .thenReturn(item);
        mockMvc.perform(get(ITEMS_ID_URL))
                .andDo(print())
                .andExpect(model().attribute(ITEM, item))
                .andExpect(view().name(ITEMS_SHOW_VIEW));
    }

    @Test
    void show() throws Exception {
        Item item = new Item(1, new Subject(), null, 0, new Audience(), 0, new Schedule());
        when(service.findById(1))
                .thenReturn(item);
        mockMvc.perform(post(ITEMS_ITEM_URL).param("id", "1"))
                .andDo(print())
                .andExpect(model().attribute(ITEM, item))
                .andExpect(view().name(ITEMS_SHOW_VIEW));
    }

    @Test
    void add() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("subjectId", "1");
        params.add("day", "Test");
        params.add("hour", "1");
        params.add("audienceId", "1");
        params.add("duration", "1");
        params.add("scheduleId", "1");
        mockMvc.perform(post(ITEMS_ADD_URL).params(params))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ITEMS_DELETE_URL).param("id", "1"))
                .andDo(print())
                .andExpect(flash().attribute("message", "Was deleted item with id - " + 1))
                .andExpect(redirectedUrl(REDIRECT));
    }
}