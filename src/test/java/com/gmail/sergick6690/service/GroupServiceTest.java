package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JpaGroupRepository;
import com.gmail.sergick6690.university.Group;
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
class GroupServiceTest {
    @Mock
    private JpaGroupRepository repository;
    @InjectMocks
    private GroupService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, RepositoryException {
        service.add(new Group());
        verify(repository).add(new Group());
    }

    @Test
    void shouldFindById() throws ServiceException, RepositoryException {
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
    void shouldInvokeFindAllGroupsRelatedToCathedra() throws RepositoryException, ServiceException {
        service.findAllGroupsRelatedToCathedra(ID);
        verify(repository).findAllGroupsRelatedToCathedra(ID);
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
        doThrow(RepositoryException.class).when(repository).add(new Group());
        assertThrows(ServiceException.class, () -> {
            service.add(new Group());
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

    @Test
    void shouldThrowServiceExceptionWhenFindAllGroupsRelatedToCathedraCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(repository).findAllGroupsRelatedToCathedra(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findAllGroupsRelatedToCathedra(anyInt());
        });
    }
}