package com.gmail.sergick6690.service;

import com.gmail.sergick6690.implementation.JdbcCathedraDAO;
import com.gmail.sergick6690.university.Cathedra;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CathedraServiceTest {
    @Mock
    private JdbcCathedraDAO dao;
    @InjectMocks
    private CathedraService service;
    private static final int ID = 1;


    @Test
    void shouldInvokeAdd() {
        service.add(new Cathedra());
        verify(dao).add(new Cathedra());
    }

    @Test
    void shouldInvokeFindById() {
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