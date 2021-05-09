package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
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
    void shouldInvokeAdd() throws ServiceException, DaoException {
        service.add(new Faculty());
        verify(dao).add(new Faculty());
    }

    @Test
    void shouldInvokeFindById() throws ServiceException, DaoException {
        service.findById(ID);
        verify(dao).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException, DaoException {
        service.findAll();
        verify(dao).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException, DaoException {
        service.removeById(ID);
        verify(dao).removeById(ID);
    }
}