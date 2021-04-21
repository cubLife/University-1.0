package com.gmail.sergick6690.service;

import com.gmail.sergick6690.implementation.JdbcItemDAO;
import com.gmail.sergick6690.university.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    private JdbcItemDAO dao;
    @InjectMocks
    private ItemService service;
    private static final int ID = 1;


    @Test
    void shouldInvokeAdd() {
        service.add(new Item());
        verify(dao).add(new Item());
    }

    @Test
    void findById() {
        service.findById(ID);
        verify(dao).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() {
        service.findAll();
        verify(dao).findAll();
    }

    @Test
    void shouldRemoveById() {
        service.removeById(ID);
        verify(dao).removeById(ID);
    }
}