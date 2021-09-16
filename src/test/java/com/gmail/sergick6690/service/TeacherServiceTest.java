package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JpaTeacherRepository;
import com.gmail.sergick6690.university.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private JpaTeacherRepository teacherRepository;
    @InjectMocks
    private TeacherService service;
    private static final String TEST = "Test";
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, RepositoryException {
        service.add(Teacher.builder().build());
        verify(teacherRepository).add(Teacher.builder().build());
    }

    @Test
    void shouldInvokeFindById() throws ServiceException, RepositoryException {
        service.findById(ID);
        verify(teacherRepository).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException, RepositoryException {
        service.findAll();
        verify(teacherRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException, RepositoryException {
        service.removeById(ID);
        verify(teacherRepository).removeById(ID);
    }

    @Test
    void shouldInvokeFindTeachersCountWithEqualDegree() throws ServiceException, RepositoryException {
        service.findTeachersCountWithEqualDegree(TEST);
        verify(teacherRepository).findTeachersCountWithEqualDegree(TEST);
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
        doThrow(RepositoryException.class).when(teacherRepository).add(new Teacher());
        assertThrows(ServiceException.class, () -> {
            service.add(new Teacher());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(teacherRepository).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(teacherRepository).findAll();
        assertThrows(ServiceException.class, () -> {
            service.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(teacherRepository).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceWhenFindAllTeachersWithEqualDegreeMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(teacherRepository).findTeachersCountWithEqualDegree(anyString());
        assertThrows(ServiceException.class, () -> {
            service.findTeachersCountWithEqualDegree(anyString());
        });
    }

}