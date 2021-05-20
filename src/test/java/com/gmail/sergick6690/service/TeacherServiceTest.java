package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcTeacherDAO;
import com.gmail.sergick6690.university.Teacher;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private JdbcTeacherDAO dao;
    @InjectMocks
    private TeacherService service;
    private static final String TEST = "Test";
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, DaoException {
        service.add(Teacher.builder().build());
        verify(dao).add(Teacher.builder().build());
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

    @Test
    void shouldInvokeFindTeachersCountWithEqualDegree() throws ServiceException, DaoException {
        service.findTeachersCountWithEqualDegree(TEST);
        verify(dao).findTeachersCountWithEqualDegree(TEST);
    }

    @Test
    void shouldInvokeRemoveSchedule() throws ServiceException, DaoException {
        service.removeSchedule(ID);
        verify(dao).removeSchedule(ID);
    }

    @Test
    void shouldInvokeAssignSchedule() throws ServiceException, DaoException {
        service.assignSchedule(ID, ID);
        verify(dao).assignSchedule(ID, ID);
    }

    @Test
    void shouldInvokeChangeSchedule() throws ServiceException, DaoException {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeSchedule(ID, ID);
        inOrder.verify(dao).removeSchedule(ID);
        inOrder.verify(dao).assignSchedule(ID, ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddMethodCall() {
        String expected = "Input parameter can't be null";
        String actual = assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        }).getMessage();
        assertTrue(expected.contains(actual));
    }

    @Test
    void shouldThrowServiceExceptionWhenAddMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).add(new Teacher());
        assertThrows(ServiceException.class, () -> {
            service.add(new Teacher());
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
    void shouldThrowServiceWhenFindAllTeachersWithEqualDegreeMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).findTeachersCountWithEqualDegree(anyString());
        assertThrows(ServiceException.class, () -> {
            service.findTeachersCountWithEqualDegree(anyString());
        });
    }

    @Test
    void shouldThrowDaoExceptionAssignScheduleWhenMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).assignSchedule(anyInt(), anyInt());
        assertThrows(ServiceException.class, () -> {
            service.assignSchedule(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionRemoveScheduleWhenMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).removeSchedule(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeSchedule(anyInt());
        });
    }
}