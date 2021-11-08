package com.gmail.sergick6690.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sergick6690.modelsForms.ItemForm;
import com.gmail.sergick6690.service.ItemService;
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
@ContextConfiguration(classes = {ItemRestController.class})
@ActiveProfiles("test")
class ItemRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @Autowired
    ObjectMapper objectMapper;
    private static final String API_ITEMS_URL = "/api/items";
    private static final String API_ITEMS_LIST_URL = "/api/items/list";

    @Test
    void add() throws Exception {
        mockMvc.perform(post(API_ITEMS_URL).
                content(objectMapper.writeValueAsString(new ItemForm(1, "monday", 13, 1, 2, 1))).
                contentType("application/json")).
                andExpect(status().isCreated()).
                andDo(print());
    }

    @Test
    void showAllItems() throws Exception {
        mockMvc.perform(get(API_ITEMS_LIST_URL)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showById() throws Exception {
        mockMvc.perform(get(API_ITEMS_URL + "/{item-id}", 1)
                .contentType("application/json").param("item-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(API_ITEMS_URL + "/{item-id}", 1)
                .contentType("application/json").param("item-id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}