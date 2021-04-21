package com.gmail.sergick6690.service;

import com.gmail.sergick6690.implementation.JdbcTeacherDAO;
import com.gmail.sergick6690.university.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private JdbcTeacherDAO dao;
    @InjectMocks
    private TeacherService service;
    private static final String TEST = "Test";
    private static final int ID = 1;


    @Test
    void shouldInvokeAdd() {
        service.add(Teacher.builder().build());
        verify(dao).add(Teacher.builder().build());
    }

    @Test
    void shouldInvokeFindById() {
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
    void shouldInvokeFindTeachersCountWithEqualDegree() {
        service.findTeachersCountWithEqualDegree(TEST);
        verify(dao).findTeachersCountWithEqualDegree(TEST);
    }

    @Test
    void shouldInvokeRemoveSchedule() {
        service.removeSchedule(ID);
        verify(dao).removeSchedule(ID);
    }

    @Test
    void shouldInvokeAssignSchedule() {
        service.assignSchedule(ID, ID);
        verify(dao).assignSchedule(ID, ID);
    }

    @Test
    void shouldInvokeChangeSchedule() {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeSchedule(ID, ID);
        inOrder.verify(dao).removeSchedule(ID);
        inOrder.verify(dao).assignSchedule(ID, ID);
        inOrder.verifyNoMoreInteractions();
    }
}