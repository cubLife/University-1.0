package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JpaCathedraRepository;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CathedraServiceTest {
    @Mock
    private JpaCathedraRepository repository;
    @InjectMocks
    private CathedraService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, RepositoryException {
        Cathedra cathedra = new Cathedra(1, "Test", new Faculty(), new ArrayList<Group>());
        service.add(cathedra);
        verify(repository).add(cathedra);
    }

    @Test
    void shouldInvokeFindById() throws ServiceException, RepositoryException {
        service.findById(ID);
        verify(repository).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException, RepositoryException {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException, RepositoryException {
        service.removeById(ID);
        verify(repository).removeById(ID);
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
        Cathedra cathedra = new Cathedra(1, "Test", new Faculty(), new ArrayList<Group>());
        doThrow(RepositoryException.class).when(repository).add(cathedra);
        assertThrows(ServiceException.class, () -> {
            service.add(cathedra);
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(repository).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(repository).findAll();
        assertThrows(ServiceException.class, () -> {
            service.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(repository).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeById(anyInt());
        });
    }
}