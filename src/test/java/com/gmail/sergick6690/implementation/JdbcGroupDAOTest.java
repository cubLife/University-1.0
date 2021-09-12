package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Faculty;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.university.Schedule;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
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

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JdbcGroupDAOTest {
    private GroupDAO groupDAO;
    private ScheduleDAO scheduleDAO;
    private CathedraDAO cathedraDAO;
    private FacultyDAO facultyDAO;
    @Mock
    private JdbcGroupDAO mockJdbcGroupDAO;
    private static final String TEST = "test";

    @Autowired
    public JdbcGroupDAOTest(GroupDAO groupDAO, ScheduleDAO scheduleDAO, CathedraDAO cathedraDAO, FacultyDAO facultyDAO) {
        this.groupDAO = groupDAO;
        this.scheduleDAO = scheduleDAO;
        this.cathedraDAO = cathedraDAO;
        this.facultyDAO = facultyDAO;
    }

    @Test
    void shouldAddGroup() throws DaoException {
        generateTestData();
        Group expected = new Group(1, TEST, null, new Schedule(1, null, null), new Cathedra(1, TEST, new Faculty(1, TEST, null), null));
        Group actual = groupDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindGroupById() throws NotImplementedException, DaoException {
        generateTestData();
        Group expected = new Group(5, TEST, null, new Schedule(1, null, null), new Cathedra(2, TEST, new Faculty(1, TEST, null), null));
        Group actual = groupDAO.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroups() throws DaoException {
        generateTestData();
        int expected = 10;
        int actual = groupDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeGroupById() throws DaoException {
        generateTestData();
        groupDAO.removeById(1);
        int expected = 9;
        int actual = groupDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddFGroupMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcGroupDAO).add(new Group());
        assertThrows(DaoException.class, () -> {
            mockJdbcGroupDAO.add(new Group());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindGroupByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcGroupDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockJdbcGroupDAO.findById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFidAllFGroupMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcGroupDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockJdbcGroupDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveFGroupByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcGroupDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockJdbcGroupDAO.removeById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllGroupsRelatedToCathedraMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcGroupDAO).findAllGroupsRelatedToCathedra(anyInt());
        assertThrows(DaoException.class, () -> {
            mockJdbcGroupDAO.findAllGroupsRelatedToCathedra(anyInt());
        });
    }

    public void generateTestData() throws DaoException {
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyDAO.add(faculty);
        cathedraDAO.add(new Cathedra(TEST, faculty));
        cathedraDAO.add(new Cathedra(TEST, faculty));
        scheduleDAO.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.add(new Group(TEST, scheduleDAO.findById(1), cathedraDAO.findById(1)));
            groupDAO.add(new Group(TEST, scheduleDAO.findById(1), cathedraDAO.findById(2)));
        }
    }
}