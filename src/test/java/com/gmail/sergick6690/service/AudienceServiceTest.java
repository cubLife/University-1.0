package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.AudienceRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.AudienceForm;
import com.gmail.sergick6690.universityModels.Audience;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ActiveProfiles("test")
class AudienceServiceTest {
    @Mock
    private AudienceRepository audienceRepository;
    @Mock
    private AudienceService mockAudienceService;
    @InjectMocks
    private AudienceService audienceService;

    @Test
    void shouldInvokeAdd() throws ServiceException {
        audienceService.add(new Audience());
        verify(audienceRepository).save(new Audience());
    }

    @Test
    void ShouldInvokeFindById() throws ServiceException {
        when(mockAudienceService.findById(anyInt())).thenReturn(new Audience());
        mockAudienceService.findById(anyInt());
        verify(mockAudienceService).findById(anyInt());
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        audienceService.findAll();
        verify(audienceRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException {
        doNothing().when(mockAudienceService).removeById(anyInt());
        mockAudienceService.removeById(anyInt());
        verify(mockAudienceService).removeById(anyInt());
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
    void shouldThrowServiceExceptionWhenAddMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockAudienceService).add(new Audience());
        assertThrows(ServiceException.class, () -> {
            mockAudienceService.add(new Audience());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockAudienceService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockAudienceService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockAudienceService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockAudienceService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockAudienceService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockAudienceService.removeById(anyInt());
        });
    }

    @Test
    void shouldCreateNewAudience() {
        AudienceForm audienceForm = new AudienceForm(1);
        Audience expected = new Audience(0, 1);
        Audience actual = audienceService.createNewAudience(audienceForm);
        assertEquals(expected, actual);
    }
}