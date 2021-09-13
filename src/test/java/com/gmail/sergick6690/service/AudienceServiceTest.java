package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.AudienceRepository;
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
    private AudienceRepository audienceDAO;
    @InjectMocks
    private AudienceService audienceService;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, DaoException {
        audienceService.add(new Audience());
        verify(audienceDAO).add(new Audience());
    }

    @Test
    void ShouldInvokeFindById() throws ServiceException, DaoException {
        audienceService.findById(ID);
        verify(audienceDAO).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException, DaoException {
        audienceService.findAll();
        verify(audienceDAO).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException, DaoException {
        audienceService.removeById(ID);
        verify(audienceDAO).removeById(ID);
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
    void shouldThrowServiceExceptionWhenAddMethodCall() throws DaoException {
        doThrow(DaoException.class).when(audienceDAO).add(new Audience());
        assertThrows(ServiceException.class, () -> {
            audienceService.add(new Audience());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(audienceDAO).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            audienceService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws DaoException {
        doThrow(DaoException.class).when(audienceDAO).findAll();
        assertThrows(ServiceException.class, () -> {
            audienceService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(audienceDAO).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            audienceService.removeById(anyInt());
        });
    }
}