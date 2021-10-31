package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.SubjectRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.SubjectForm;
import com.gmail.sergick6690.universityModels.Subject;
import com.gmail.sergick6690.universityModels.Teacher;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private SubjectService mockSubjectService;
    @InjectMocks
    private SubjectService service;
    private static final int VALUE = 1;
    private static final String TEST = "Test";

    @Test
    void shouldInvokeAdd() throws ServiceException {
        service.add(new Subject());
        verify(subjectRepository).save(new Subject());
    }

    @Test
    void shouldFindById() throws ServiceException {
        mockSubjectService.findById(anyInt());
        verify(mockSubjectService).findById(anyInt());
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        service.findAll();
        verify(subjectRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException {
        doNothing().when(mockSubjectService).removeById(anyInt());
        mockSubjectService.removeById(anyInt());
        verify(mockSubjectService).removeById(anyInt());
    }

    @Test
    void shouldCreateNewSubject() throws ServiceException {
        SubjectForm subjectForm = new SubjectForm(TEST, VALUE, TEST);
        when(mockSubjectService.createNewSubject(subjectForm)).thenReturn(new Subject(1, TEST, new Teacher(), TEST));
        Subject expected = new Subject(1, TEST, new Teacher(), TEST);
        Subject actual = mockSubjectService.createNewSubject(subjectForm);
        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void shouldInvokeFindAllSubjectRelatedToAudience() {
        service.findAllSubjectRelatedToAudience(VALUE);
        verify(subjectRepository).findAllSubjectRelatedToAudience(VALUE);
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
        doThrow(ServiceException.class).when(mockSubjectService).add(new Subject());
        assertThrows(ServiceException.class, () -> {
            mockSubjectService.add(new Subject());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockSubjectService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockSubjectService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockSubjectService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockSubjectService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockSubjectService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockSubjectService.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllSubjectRelatedToAudienceMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockSubjectService).findAllSubjectRelatedToAudience(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockSubjectService.findAllSubjectRelatedToAudience(anyInt());
        });
    }
}