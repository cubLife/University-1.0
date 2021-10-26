package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.ScheduleRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.universityModels.Schedule;
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
class ScheduleServiceTest {
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleService mockScheduleService;
    @InjectMocks
    private ScheduleService service;

    @Test
    void shouldInvokeAdd() throws ServiceException {
        service.add(new Schedule());
        verify(scheduleRepository).save(new Schedule());
    }

    @Test
    void shouldInvokeFindById() throws ServiceException {
        when(mockScheduleService.findById(anyInt())).thenReturn(new Schedule());
        mockScheduleService.findById(anyInt());
        verify(mockScheduleService).findById(anyInt());
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        service.findAll();
        verify(scheduleRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException {
        doNothing().when(mockScheduleService).removeById(anyInt());
        mockScheduleService.removeById(anyInt());
        verify(mockScheduleService).removeById(anyInt());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddMethodCall() {
        String expected = "Input Parameter can't be null";
        String actual = assertThrows(IllegalArgumentException.class, () -> {
            service.add(null);
        }).getMessage();
        assertTrue(expected.contains(actual));
    }

    @Test
    void shouldThrowServiceExceptionWhenAddMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockScheduleService).add(new Schedule());
        assertThrows(ServiceException.class, () -> {
            mockScheduleService.add(new Schedule());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockScheduleService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockScheduleService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockScheduleService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockScheduleService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockScheduleService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockScheduleService.removeById(anyInt());
        });
    }
}