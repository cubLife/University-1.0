package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JpaSubjectRepository;
import com.gmail.sergick6690.university.Subject;
import lombok.SneakyThrows;
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
class SubjectServiceTest {
    @Mock
    private JpaSubjectRepository subjectRepository;
    @InjectMocks
    private SubjectService service;
    private static final int ID = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException, RepositoryException {
        service.add(new Subject());
        verify(subjectRepository).add(new Subject());
    }

    @Test
    void shouldFindById() throws ServiceException, RepositoryException{
        service.findById(ID);
        verify(subjectRepository).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException, RepositoryException {
        service.findAll();
        verify(subjectRepository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException, RepositoryException {
        service.removeById(ID);
        verify(subjectRepository).removeById(ID);
    }

    @SneakyThrows
    @Test
    void shouldInvokeFindAllSubjectRelatedToAudience() {
        service.findAllSubjectRelatedToAudience(ID);
        verify(subjectRepository).findAllSubjectRelatedToAudience(ID);
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
        doThrow(RepositoryException.class).when(subjectRepository).add(new Subject());
        assertThrows(ServiceException.class, () -> {
            service.add(new Subject());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(subjectRepository).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(subjectRepository).findAll();
        assertThrows(ServiceException.class, () -> {
            service.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(subjectRepository).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllSubjectRelatedToAudienceMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(subjectRepository).findAllSubjectRelatedToAudience(anyInt());
        assertThrows(ServiceException.class, () -> {
            service.findAllSubjectRelatedToAudience(anyInt());
        });
    }

}