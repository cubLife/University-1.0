package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JpaAudienceRepository;
import com.gmail.sergick6690.university.Audience;
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
class AudienceServiceTest {
    @Mock
    private JpaAudienceRepository audienceRepository;
    @InjectMocks
    private AudienceService audienceService;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, RepositoryException {
        audienceService.add(new Audience());
        verify(audienceRepository).add(new Audience());
    }

    @Test
    void ShouldInvokeFindById() throws ServiceException, RepositoryException {
        audienceService.findById(ID);
        verify(audienceRepository).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException, RepositoryException {
        audienceService.findAll();
        verify(audienceRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException, RepositoryException {
        audienceService.removeById(ID);
        verify(audienceRepository).removeById(ID);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAddMethodCall() {
        String expected = "Input parameter can't be null";
        String actual = assertThrows(IllegalArgumentException.class, () -> {
            audienceService.add(null);
        }).getMessage();
        assertTrue(expected.contains(actual));
    }

    @Test
    void shouldThrowServiceExceptionWhenAddMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(audienceRepository).add(new Audience());
        assertThrows(ServiceException.class, () -> {
            audienceService.add(new Audience());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(audienceRepository).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            audienceService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(audienceRepository).findAll();
        assertThrows(ServiceException.class, () -> {
            audienceService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(audienceRepository).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            audienceService.removeById(anyInt());
        });
    }
}