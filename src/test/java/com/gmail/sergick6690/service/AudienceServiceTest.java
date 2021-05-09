package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import com.gmail.sergick6690.university.Audience;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AudienceServiceTest {
    @Mock
    private JdbcAudienceDAO audienceDAO;
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
}