package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.StudentRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentDAO;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, DaoException {
        service.add(Student.builder().build());
        verify(studentDAO).add(Student.builder().build());
    }

    @Test
    void shouldFindById() throws ServiceException, DaoException {
        service.findById(ID);
        verify(studentDAO).findById(ID);
    }

    @Test
    void shouldFindAll() throws ServiceException, DaoException {
        service.findAll();
        verify(studentDAO).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException, DaoException {
        service.removeById(ID);
        verify(studentDAO).removeById(ID);
    }


    @Test
    void shouldAssignCourse() throws ServiceException, DaoException {
        service.assignCourse(ID, ID);
        verify(studentDAO).assignCourse(ID, ID);
    }

    @Test
    void shouldRemoveFromCourse() throws ServiceException, DaoException {
        service.removeFromCourse(ID);
        verify(studentDAO).removeFromCourse(ID);
    }


    @Test
    void shouldInvokeChangeCourse() throws ServiceException, DaoException {
        InOrder inOrder = Mockito.inOrder(studentDAO);
        service.changeCourse(ID, ID);
        inOrder.verify(studentDAO).removeFromCourse(ID);
        inOrder.verify(studentDAO).assignCourse(ID, ID);
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
        doThrow(DaoException.class).when(studentDAO).add(new Student());
        assertThrows(ServiceException.class, () -> {
            service.add(new Student());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(studentDAO).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws DaoException {
        doThrow(DaoException.class).when(studentDAO).findAll();
        assertThrows(ServiceException.class, () -> {
            service.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(studentDAO).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenAssignCourseMethodCall() throws DaoException {
        doThrow(DaoException.class).when(studentDAO).assignCourse(anyInt(), anyInt());
        assertThrows(ServiceException.class, () -> {
            service.assignCourse(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenAssignGroupMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(studentService).assignGroup(anyInt(), anyInt());
        assertThrows(ServiceException.class, () -> {
            studentService.assignGroup(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveFromCourseMethodCall() throws DaoException {
        doThrow(DaoException.class).when(studentDAO).removeFromCourse(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeFromCourse(anyInt());
        });
    }
}