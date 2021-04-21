package com.gmail.sergick6690.service;

import com.gmail.sergick6690.implementation.JdbcFacultyDAO;
import com.gmail.sergick6690.university.Faculty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    private JdbcFacultyDAO dao;
    @InjectMocks
    private FacultyService service;
    private static final int ID = 1;


    @Test
    void shouldInvokeAdd() {
        service.add(new Faculty());
        verify(dao).add(new Faculty());
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
    void shouldInvokeRemoveById() {
        service.removeById(ID);
        verify(dao).removeById(ID);
    }
}