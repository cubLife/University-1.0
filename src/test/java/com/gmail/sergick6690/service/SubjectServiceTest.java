package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcSubjectDAO;
import com.gmail.sergick6690.university.Subject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {
    @Mock
    private JdbcSubjectDAO dao;
    @InjectMocks
    private SubjectService service;
    private static final int ID = 1;


    @Test
    void shouldInvokeAdd() throws ServiceException, DaoException {
        service.add(new Subject());
        verify(dao).add(new Subject());
    }

    @Test
    void shouldFindById() throws ServiceException, DaoException {
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

    @SneakyThrows
    @Test
    void shouldInvokeFindAllSubjectRelatedToAudience() throws ServiceException {
        service.findAllSubjectRelatedToAudience(ID);
        verify(dao).findAllSubjectRelatedToAudience(ID);
    }

    @SneakyThrows
    @Test
    void assignTeacher() {
        service.assignTeacher(ID, ID);
        verify(dao).assignTeacher(ID, ID);
    }

    @Test
    void removeTeacher() throws ServiceException, DaoException {
        service.removeTeacher(ID);
        verify(dao).removeTeacher(ID);
    }

    @Test
    void shouldInvokeChangeTeacher() throws ServiceException, DaoException {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeTeacher(ID, ID);
        inOrder.verify(dao).removeTeacher(ID);
        inOrder.verify(dao).assignTeacher(ID, ID);
        inOrder.verifyNoMoreInteractions();
    }
}