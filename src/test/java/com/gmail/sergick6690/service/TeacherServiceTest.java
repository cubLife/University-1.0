package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.TeacherRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.universityModels.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private TeacherService mockTeacherService;
    @InjectMocks
    private TeacherService service;
    private static final String TEST = "Test";

    @Test
    void shouldInvokeAdd() throws ServiceException {
        service.add(Teacher.builder().build());
        verify(teacherRepository).save(Teacher.builder().build());
    }

    @Test
    void shouldInvokeFindById() throws ServiceException {
        when(mockTeacherService.findById(anyInt())).thenReturn(new Teacher());
        mockTeacherService.findById(anyInt());
        verify(mockTeacherService).findById(anyInt());
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        service.findAll();
        verify(teacherRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException {
        doNothing().when(mockTeacherService).removeById(anyInt());
        mockTeacherService.removeById(anyInt());
        verify(mockTeacherService).removeById(anyInt());
    }

    @Test
    void shouldInvokeFindTeachersCountWithEqualDegree() throws ServiceException {
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
    void shouldThrowServiceExceptionWhenAddMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockTeacherService).add(new Teacher());
        assertThrows(ServiceException.class, () -> {
            mockTeacherService.add(new Teacher());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockTeacherService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockTeacherService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockTeacherService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockTeacherService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockTeacherService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockTeacherService.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceWhenFindAllTeachersWithEqualDegreeMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockTeacherService).findTeachersCountWithEqualDegree(anyString());
        assertThrows(ServiceException.class, () -> {
            mockTeacherService.findTeachersCountWithEqualDegree(anyString());
        });
    }
}