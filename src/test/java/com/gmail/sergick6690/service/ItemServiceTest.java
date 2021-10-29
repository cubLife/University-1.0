package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.ItemRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.ItemForm;
import com.gmail.sergick6690.universityModels.Audience;
import com.gmail.sergick6690.universityModels.Item;
import com.gmail.sergick6690.universityModels.Schedule;
import com.gmail.sergick6690.universityModels.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    private ItemRepository repository;
    @Mock
    private ItemService mockItemService;
    @InjectMocks
    private ItemService service;
    private static final int VALUE = 1;

    @Test
    void shouldInvokeAdd() throws ServiceException {
        service.add(new Item());
        verify(repository).save(new Item());
    }

    @Test
    void findById() throws ServiceException {
        when(mockItemService.findById(anyInt())).thenReturn(new Item());
        mockItemService.findById(anyInt());
        verify(mockItemService).findById(anyInt());
    }

    @Test
    void shouldInvokeFindAll() throws ServiceException {
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    void shouldInvokeRemoveById() throws ServiceException {
        doNothing().when(mockItemService).removeById(anyInt());
        mockItemService.removeById(anyInt());
        verify(mockItemService).removeById(anyInt());
    }

    @Test
    void shouldCreateNewItem() throws ServiceException {
        ItemForm itemForm = new ItemForm(VALUE, "Monday", VALUE, VALUE, VALUE, VALUE);
        when(mockItemService.createNewItem(itemForm)).thenReturn(new Item(VALUE, new Subject(), "monday", 17, new Audience(), 1, new Schedule()));
        Item expected = new Item(VALUE, new Subject(), "monday", 17, new Audience(), 1, new Schedule());
        Item actual = mockItemService.createNewItem(itemForm);
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
        doThrow(ServiceException.class).when(mockItemService).add(new Item());
        assertThrows(ServiceException.class, () -> {
            mockItemService.add(new Item());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockItemService).findById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockItemService.findById(anyInt());
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenFindAllMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockItemService).findAll();
        assertThrows(ServiceException.class, () -> {
            mockItemService.findAll();
        });
    }

    @Test
    void shouldThrowServiceExceptionWhenRemoveByIdMethodCall() throws ServiceException {
        doThrow(ServiceException.class).when(mockItemService).removeById(anyInt());
        assertThrows(ServiceException.class, () -> {
            mockItemService.removeById(anyInt());
        });
    }
}