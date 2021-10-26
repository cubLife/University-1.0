package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.StudentRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.universityModels.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentService mockStudentService;
    @InjectMocks
    private StudentService service;

    @Test
    void shouldInvokeAdd() throws ServiceException {
        service.add(Student.builder().build());
        verify(studentRepository).save(Student.builder().build());
    }

    @Test
    void shouldFindById() throws ServiceException {
        when(mockStudentService.findById(anyInt())).thenReturn(new Student());
        mockStudentService.findById(anyInt());
        verify(mockStudentService).findById(anyInt());
    }

    @Test
    void shouldFindAll() throws ServiceException {
        service.findAll();
        verify(studentRepository).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException {
        mockStudentService.removeById(anyInt());
        verify(mockStudentService).removeById(anyInt());
    }


    @Test
    void shouldAssignCourse() throws ServiceException {
        mockStudentService.assignCourse(anyInt(), anyInt());
        verify(mockStudentService).assignCourse(anyInt(), anyInt());
    }

    @Test
    void shouldRemoveFromCourse() throws ServiceException {
        doNothing().when(mockStudentService).removeFromCourse(anyInt());
        mockStudentService.removeFromCourse(anyInt());
        verify(mockStudentService).removeFromCourse(anyInt());
    }


    @Test
    void shouldInvokeChangeCourse() throws ServiceException {
        doNothing().when(mockStudentService).changeCourse(anyInt(), anyInt());
        mockStudentService.changeCourse(anyInt(), anyInt());
        verify(mockStudentService).changeCourse(anyInt(), anyInt());
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
        doThrow(ServiceException.class).when(mockStudentService).add(new Student());
        assertThrows(ServiceException.class, () -> {
            mockStudentService.add(new Student());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockStudentService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockStudentService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockStudentService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockStudentService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockStudentService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockStudentService.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenAssignCourseMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockStudentService).assignCourse(anyInt(), anyInt());
        assertThrows(ServiceException.class, () -> {
            mockStudentService.assignCourse(anyInt(), anyInt());
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
    void shouldThrowDaoExceptionWhenRemoveFromCourseMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockStudentService).removeFromCourse(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockStudentService.removeFromCourse(anyInt());
        });
    }
}