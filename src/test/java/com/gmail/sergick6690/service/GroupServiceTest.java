package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.GroupRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.GroupForm;
import com.gmail.sergick6690.universityModels.Cathedra;
import com.gmail.sergick6690.universityModels.Group;
import com.gmail.sergick6690.universityModels.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupRepository repository;
    @Mock
    private GroupService mockService;
    @InjectMocks
    private GroupService service;
    private static final int VALUE = 1;
    private static final String TEST = "TEST";

    @Test
    void shouldInvokeAdd() throws ServiceException {
        service.add(new Group());
        verify(repository).save(new Group());
    }

    @Test
    void shouldFindById() throws ServiceException {
        when(mockService.findById(anyInt())).thenReturn(new Group());
        mockService.findById(anyInt());
        verify(mockService).findById(anyInt());

    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    void shouldRemoveById() throws ServiceException {
        doNothing().when(mockService).removeById(anyInt());
        mockService.removeById(anyInt());
        verify(mockService).removeById(anyInt());
    }

    @Test
    void shouldInvokeFindAllGroupsRelatedToCathedra() throws ServiceException {
        service.findAllGroupsRelatedToCathedra(VALUE);
        verify(repository).findAllByCathedra_Id(VALUE);
    }

    @Test
    void shouldCreateNewGroup() throws ServiceException {
        GroupForm groupForm = new GroupForm(TEST, VALUE, VALUE);
        when(mockService.createNewGroup(groupForm)).thenReturn(new Group(VALUE, TEST, null, new Schedule(), new Cathedra()));
        Group expected = new Group(VALUE, TEST, null, new Schedule(), new Cathedra());
        Group actual = mockService.createNewGroup(groupForm);
        assertEquals(expected, actual);
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
        doThrow(ServiceException.class).when(mockService).add(new Group());
        assertThrows(ServiceException.class, () -> {
            mockService.add(new Group());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() {
        assertThrows(ServiceException.class, () -> {
            service.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockService.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllGroupsRelatedToCathedraCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockService).findAllGroupsRelatedToCathedra(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockService.findAllGroupsRelatedToCathedra(anyInt());
        });
    }
}