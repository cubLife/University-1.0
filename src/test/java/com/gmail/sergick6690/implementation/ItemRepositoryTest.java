package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.*;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.*;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
@ActiveProfiles("test")
class ItemRepositoryTest {
    private ItemDAO itemDAO;
    private ScheduleDAO scheduleDAO;
    private SubjectDAO subjectDAO;
    private AudienceDAO audienceDAO;
    private TeacherDAO teacherDAO;
    @Mock
    private ItemRepository mockItemRepository;
    private static final String TEST = "Test";

    @Autowired
    public ItemRepositoryTest(ItemDAO itemDAO, ScheduleDAO scheduleDAO,
                              SubjectDAO subjectDAO, AudienceDAO audienceDAO, TeacherDAO teacherDAO) {
        this.itemDAO = itemDAO;
        this.scheduleDAO = scheduleDAO;
        this.subjectDAO = subjectDAO;
        this.audienceDAO = audienceDAO;
        this.teacherDAO = teacherDAO;
    }

    @Test
    void shouldAddItem() throws DaoException {
        generateTestData();
        Item expected = new Item(1, new Subject(1, null, null, null), TEST, 1, new Audience(1, 0), 1, new Schedule(1, null, null));
        Item actual = itemDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindItemById() throws NotImplementedException, DaoException {
        generateTestData();
        Item expected = new Item(5, new Subject(1, null, null, null), TEST, 1, new Audience(1, 0), 1, new Schedule(1, null, null));
        Item actual = itemDAO.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllItems() throws DaoException {
        generateTestData();
        int expected = 5;
        int actual = itemDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveItemsById() throws DaoException {
        generateTestData();
        itemDAO.removeById(5);
        int expected = 4;
        int actual = itemDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddItemMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockItemRepository).add(new Item());
        assertThrows(DaoException.class, () -> {
            mockItemRepository.add(new Item());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindByIdItemMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockItemRepository).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockItemRepository.findById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllItemMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockItemRepository).findAll();
        assertThrows(DaoException.class, () -> {
            mockItemRepository.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockItemRepository).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockItemRepository.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindByIdMethodCall() throws DaoException {
        doThrow(IllegalArgumentException.class).when(mockItemRepository).removeById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockItemRepository.removeById(anyInt());
        });
    }

    private void generateTestData() throws DaoException {
        Schedule schedule = new Schedule(TEST);
        scheduleDAO.add(schedule);
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).build();
        Audience audience = new Audience();
        teacherDAO.add(teacher);
        Subject subject = new Subject(TEST, teacherDAO.findById(1), TEST);
        subjectDAO.add(subject);
        audienceDAO.add(audience);
        for (int i = 0; i < 5; i++) {
            itemDAO.add(new Item(subjectDAO.findById(1), TEST, 1, audienceDAO.findById(1), 1, scheduleDAO.findById(1)));
        }
    }
}