package com.gmail.sergick6690.service;

import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import com.gmail.sergick6690.university.Audience;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AudienceServiceTest {
    @Mock
    private JdbcAudienceDAO audienceDAO;
    @InjectMocks
    private AudienceService audienceService;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() {
        audienceService.add(new Audience());
        verify(audienceDAO).add(new Audience());

    }

    @Test
    void ShouldInvokeFindById() {
        audienceService.findById(ID);
        verify(audienceDAO).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() {
        audienceService.findAll();
        verify(audienceDAO).findAll();

    }

    @Test
    void shouldInvokeRemoveById() {
        audienceService.removeById(ID);
        verify(audienceDAO).removeById(ID);
    }
}