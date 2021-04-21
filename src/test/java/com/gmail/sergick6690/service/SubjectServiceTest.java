package com.gmail.sergick6690.service;

import com.gmail.sergick6690.implementation.JdbcSubjectDAO;
import com.gmail.sergick6690.university.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {
    @Mock
    private JdbcSubjectDAO dao;
    @InjectMocks
    private SubjectService service;
    private static final int ID = 1;


    @Test
    void shouldInvokeAdd() {
        service.add(new Subject());
        verify(dao).add(new Subject());
    }

    @Test
    void shouldFindById() {
        service.findById(ID);
        verify(dao).findById(ID);
    }

    @Test
    void shouldInvokeFindAll() {
        service.findAll();
        verify(dao).findAll();
    }

    @Test
    void shouldInvokeRemoveById() {
        service.removeById(ID);
        verify(dao).removeById(ID);
    }

    @Test
    void shouldInvokeFindAllSubjectRelatedToAudience() {
        service.findAllSubjectRelatedToAudience(ID);
        verify(dao).findAllSubjectRelatedToAudience(ID);
    }

    @Test
    void assignTeacher() {
        service.assignTeacher(ID, ID);
        verify(dao).assignTeacher(ID, ID);
    }

    @Test
    void removeTeacher() {
        service.removeTeacher(ID);
        verify(dao).removeTeacher(ID);
    }

    @Test
    void shouldInvokeChangeTeacher() {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeTeacher(ID, ID);
        inOrder.verify(dao).removeTeacher(ID);
        inOrder.verify(dao).assignTeacher(ID, ID);
        inOrder.verifyNoMoreInteractions();
    }
}