package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.*;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.*;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
class JdbcItemDAOTest {
    private TablesCreator creator;
    private ItemDAO itemDAO;
    private ScheduleDAO scheduleDAO;
    private SubjectDAO subjectDAO;
    private AudienceDAO audienceDAO;
    private TeacherDAO teacherDAO;
    @Mock
    private JdbcItemDAO mockJdbcItemDAO;
    private static final String TEST = "Test";

    @Autowired
    public JdbcItemDAOTest(TablesCreator creator, ItemDAO itemDAO, ScheduleDAO scheduleDAO,
                           SubjectDAO subjectDAO, AudienceDAO audienceDAO, TeacherDAO teacherDAO) {
        this.creator = creator;
        this.itemDAO = itemDAO;
        this.scheduleDAO = scheduleDAO;
        this.subjectDAO = subjectDAO;
        this.audienceDAO = audienceDAO;
        this.teacherDAO = teacherDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddItem() throws DaoException {
        generateTestData();
        Item expected = new Item(1,1, TEST, 1, 1, 1, 1);
        Item actual = itemDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindItemById() throws NotImplementedException, DaoException {
        generateTestData();
        Item expected = new Item(3,1, TEST, 1, 1, 1, 1);
        Item actual = itemDAO.findById(3);
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
        itemDAO.removeById(1);
        int expected = 4;
        int actual = itemDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddItemMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcItemDAO).add(new Item());
        assertThrows(DaoException.class, () -> {
            mockJdbcItemDAO.add(new Item());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindByIdItemMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcItemDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockJdbcItemDAO.findById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllItemMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcItemDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockJdbcItemDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcItemDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockJdbcItemDAO.removeById(anyInt());
        });
    }

    private void generateTestData() throws DaoException {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = Teacher.builder().id(1).firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).
                scheduleId(1).subjects(null).build();
        Subject subject = new Subject(1, TEST, 1, TEST);
        Audience audience = new Audience(1, 0);
        scheduleDAO.add(schedule);
        teacherDAO.add(teacher);
        subjectDAO.add(subject);
        audienceDAO.add(audience);
        for (int i = 0; i < 5; i++) {
            itemDAO.add(new Item(1, TEST,1, 1, 1, 1));
        }
    }
}