package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.FacultyRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.FacultyForm;
import com.gmail.sergick6690.universityModels.Faculty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    private FacultyRepository mockRepository;
    @Mock
    private FacultyService mockFacultyService;
    @InjectMocks
    private FacultyService service;

    @Test
    void shouldInvokeAdd() throws ServiceException {
        service.add(new Faculty());
        verify(mockRepository).save(new Faculty());
    }

    @Test
    void shouldInvokeFindById() throws ServiceException {
        when(mockFacultyService.findById(anyInt())).thenReturn(new Faculty());
        mockFacultyService.findById(anyInt());
        verify(mockFacultyService).findById(anyInt());
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        service.findAll();
        verify(mockRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException {
        mockFacultyService.removeById(anyInt());
        verify(mockFacultyService).removeById(anyInt());
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
        doThrow(ServiceException.class).when(mockFacultyService).add(new Faculty());
        assertThrows(ServiceException.class, () -> {
            mockFacultyService.add(new Faculty());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockFacultyService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockFacultyService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockFacultyService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockFacultyService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockFacultyService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockFacultyService.removeById(anyInt());
        });
    }

    @Test
    void shouldCreateNewFaculty() {
        FacultyForm facultyForm = new FacultyForm("Test");
        Faculty expected = new Faculty("Test");
        Faculty actual = service.createNewFaulty(facultyForm);
        assertEquals(expected, actual);
    }
}