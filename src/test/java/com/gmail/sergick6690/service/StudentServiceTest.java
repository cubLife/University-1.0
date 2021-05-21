package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcStudentDAO;
import com.gmail.sergick6690.university.Student;
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
class StudentServiceTest {
    @Mock
    private JdbcStudentDAO dao;
    @InjectMocks
    private StudentService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, DaoException {
        service.add(Student.builder().build());
        verify(dao).add(Student.builder().build());
    }

    @Test
    void shouldFindById() throws ServiceException, DaoException {
        service.findById(ID);
        verify(dao).findById(ID);
    }

    @Test
    void shouldFindAll() throws ServiceException, DaoException {
        service.findAll();
        verify(dao).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException, DaoException {
        service.removeById(ID);
        verify(dao).removeById(ID);
    }

    @Test
    void shouldAssignGroup() throws ServiceException, DaoException {
        service.assignGroup(ID, ID);
        verify(dao).assignGroup(ID, ID);
    }

    @Test
    void shouldRemoveFromGroup() throws ServiceException, DaoException {
        service.removeFromGroup(ID);
        verify(dao).removeFromGroup(ID);
    }

    @Test
    void shouldAssignCourse() throws ServiceException, DaoException {
        service.assignCourse(ID, ID);
        verify(dao).assignCourse(ID, ID);
    }

    @Test
    void shouldRemoveFromCourse() throws ServiceException, DaoException {
        service.removeFromCourse(ID);
        verify(dao).removeFromCourse(ID);
    }

    @Test
    void shouldInvokeChangeGroup() throws ServiceException, DaoException {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeGroup(ID, ID);
        inOrder.verify(dao).removeFromGroup(ID);
        inOrder.verify(dao).assignGroup(ID, ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldInvokeChangeCourse() throws ServiceException, DaoException {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeCourse(ID, ID);
        inOrder.verify(dao).removeFromCourse(ID);
        inOrder.verify(dao).assignCourse(ID, ID);
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
        doThrow(DaoException.class).when(dao).add(new Student());
        assertThrows(ServiceException.class, () -> {
            service.add(new Student());
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
    void shouldThrowServiceExceptionWhenAssignCourseMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).assignCourse(anyInt(),anyInt());
        assertThrows(ServiceException.class, () -> {
            service.assignCourse(anyInt(),anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenAssignGroupMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).assignGroup(anyInt(),anyInt());
        assertThrows(ServiceException.class, () -> {
            service.assignGroup(anyInt(),anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveFromCourseMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).removeFromCourse(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeFromCourse(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveFromGroupMethodCall() throws DaoException {
        doThrow(DaoException.class).when(dao).removeFromGroup(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeFromGroup(anyInt());
        });
    }
}