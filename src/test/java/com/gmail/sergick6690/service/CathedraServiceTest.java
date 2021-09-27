package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.CathedraRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Faculty;
import com.gmail.sergick6690.university.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CathedraServiceTest {
    @Mock
    private CathedraRepository repository;
    @Mock
    private CathedraService mockCathedraService;
    @InjectMocks
    private CathedraService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException {
        Cathedra cathedra = new Cathedra(ID, "Test", new Faculty(), new ArrayList<Group>());
        service.add(cathedra);
        verify(repository).save(cathedra);
    }

    @Test
    void shouldInvokeFindById() throws ServiceException {
        mockCathedraService.findById(anyInt());
        verify(mockCathedraService).findById(anyInt());
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException {
        doNothing().when(mockCathedraService).removeById(anyInt());
        mockCathedraService.removeById(ID);
        verify(mockCathedraService).removeById(anyInt());
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
        Cathedra cathedra = new Cathedra(ID, "Test", new Faculty(), new ArrayList<Group>());
        doThrow(ServiceException.class).when(mockCathedraService).add(cathedra);
        assertThrows(ServiceException.class, () -> {
            mockCathedraService.add(cathedra);
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockCathedraService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockCathedraService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockCathedraService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockCathedraService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockCathedraService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockCathedraService.removeById(anyInt());
        });
    }
}