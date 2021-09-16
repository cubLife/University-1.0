package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JpaStudentRepository;
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
    private JpaStudentRepository studentRepository;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, RepositoryException {
        service.add(Student.builder().build());
        verify(studentRepository).add(Student.builder().build());
    }

    @Test
    void shouldFindById() throws ServiceException, RepositoryException {
        service.findById(ID);
        verify(studentRepository).findById(ID);
    }

    @Test
    void shouldFindAll() throws ServiceException, RepositoryException {
        service.findAll();
        verify(studentRepository).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException, RepositoryException {
        service.removeById(ID);
        verify(studentRepository).removeById(ID);
    }


    @Test
    void shouldAssignCourse() throws ServiceException, RepositoryException {
        service.assignCourse(ID, ID);
        verify(studentRepository).assignCourse(ID, ID);
    }

    @Test
    void shouldRemoveFromCourse() throws ServiceException, RepositoryException {
        service.removeFromCourse(ID);
        verify(studentRepository).removeFromCourse(ID);
    }


    @Test
    void shouldInvokeChangeCourse() throws ServiceException, RepositoryException {
        InOrder inOrder = Mockito.inOrder(studentRepository);
        service.changeCourse(ID, ID);
        inOrder.verify(studentRepository).removeFromCourse(ID);
        inOrder.verify(studentRepository).assignCourse(ID, ID);
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
    void shouldThrowServiceExceptionWhenAddMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(studentRepository).add(new Student());
        assertThrows(ServiceException.class, () -> {
            service.add(new Student());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(studentRepository).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(studentRepository).findAll();
        assertThrows(ServiceException.class, () -> {
            service.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(studentRepository).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenAssignCourseMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(studentRepository).assignCourse(anyInt(), anyInt());
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
    void shouldThrowDaoExceptionWhenRemoveFromCourseMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(studentRepository).removeFromCourse(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeFromCourse(anyInt());
        });
    }
}