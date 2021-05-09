package com.gmail.sergick6690.service;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcStudentDAO;
import com.gmail.sergick6690.university.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private JdbcStudentDAO dao;
    @InjectMocks
    private StudentService service;
    private static final int ID = 1;


    @Test
    void shouldInvokeAdd() throws ServiceException, DaoException {
        service.add(Student.builder().build());
        verify(dao).add(Student.builder().build());
    }

    @Test
    void shouldFindById() throws ServiceException, DaoException {
        service.findById(ID);
        verify(dao).findById(ID);
    }

    @Test
    void shouldFindAll() throws ServiceException, DaoException {
        service.findAll();
        verify(dao).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException, DaoException {
        service.removeById(ID);
        verify(dao).removeById(ID);
    }

    @Test
    void shouldAssignGroup() throws ServiceException, DaoException {
        service.assignGroup(ID, ID);
        verify(dao).assignGroup(ID, ID);
    }

    @Test
    void shouldRemoveFromGroup() throws ServiceException, DaoException {
        service.removeFromGroup(ID);
        verify(dao).removeFromGroup(ID);
    }

    @Test
    void shouldAssignCourse() throws ServiceException, DaoException {
        service.assignCourse(ID, ID);
        verify(dao).assignCourse(ID, ID);
    }

    @Test
    void shouldRemoveFromCourse() throws ServiceException, DaoException {
        service.removeFromCourse(ID);
        verify(dao).removeFromCourse(ID);
    }

    @Test
    void shouldInvokeChangeGroup() throws ServiceException, DaoException {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeGroup(ID, ID);
        inOrder.verify(dao).removeFromGroup(ID);
        inOrder.verify(dao).assignGroup(ID, ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldInvokeChangeCourse() throws ServiceException, DaoException {
        InOrder inOrder = Mockito.inOrder(dao);
        service.changeCourse(ID, ID);
        inOrder.verify(dao).removeFromCourse(ID);
        inOrder.verify(dao).assignCourse(ID, ID);
        inOrder.verifyNoMoreInteractions();
    }
}