package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JpaScheduleRepository;
import com.gmail.sergick6690.university.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
    @Mock
    private JpaScheduleRepository scheduleRepository;
    @InjectMocks
    private ScheduleService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, RepositoryException {
        service.add(new Schedule());
        verify(scheduleRepository).add(new Schedule());
    }

    @Test
    void shouldInvokeFindById() throws ServiceException, RepositoryException {
        service.findById(ID);
        verify(scheduleRepository).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException, RepositoryException {
        service.findAll();
        verify(scheduleRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException, RepositoryException {
        service.removeById(ID);
        verify(scheduleRepository).removeById(ID);
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
    void shouldThrowServiceExceptionWhenAddMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(scheduleRepository).add(new Schedule());
        assertThrows(ServiceException.class, () -> {
            service.add(new Schedule());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(scheduleRepository).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(scheduleRepository).findAll();
        assertThrows(ServiceException.class, () -> {
            service.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(scheduleRepository).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeById(anyInt());
        });
    }
}