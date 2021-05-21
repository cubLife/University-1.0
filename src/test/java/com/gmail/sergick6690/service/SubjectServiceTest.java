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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
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

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddMethodCall(){
        String expected = "Input parameter can't be null";
        String actual = assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        }).getMessage();
        assertTrue(expected.contains(actual));
    }

    @Test
    void shouldThrowServiceExceptionWhenAddMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).add(new Subject());
        assertThrows(ServiceException.class, () -> {
            service.add(new Subject());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).findAll();
        assertThrows(ServiceException.class, () -> {
            service.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllSubjectRelatedToAudienceMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).findAllSubjectRelatedToAudience(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findAllSubjectRelatedToAudience(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenAssignTeacherMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).assignTeacher(anyInt(),anyInt());
        assertThrows(ServiceException.class, () -> {
            service.assignTeacher(anyInt(),anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveTeacherMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).removeTeacher(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeTeacher(anyInt());
        });
    }
}