package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.university.Schedule;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
class JdbcGroupDAOTest {
    private TablesCreator creator;
    private GroupDAO groupDAO;
    private ScheduleDAO scheduleDAO;
    private CathedraDAO cathedraDAO;
    private static final String TEST = "test";

    @Autowired
    public JdbcGroupDAOTest(TablesCreator creator, GroupDAO groupDAO, ScheduleDAO scheduleDAO, CathedraDAO cathedraDAO) {
        this.creator = creator;
        this.groupDAO = groupDAO;
        this.scheduleDAO = scheduleDAO;
        this.cathedraDAO = cathedraDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddGroup() throws DaoException {
        cathedraDAO.add(new Cathedra());
        scheduleDAO.add(new Schedule());
        groupDAO.add(new Group(TEST, 1, 1));
        Group expected = new Group(1, TEST, null, 1, 1);
        Group actual = groupDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindGroupById() throws NotImplementedException, DaoException {
        cathedraDAO.add(new Cathedra());
        scheduleDAO.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.add(new Group(TEST, 1, 1));
        }
        Group expected = new Group(2, TEST, null, 1, 1);
        Group actual = groupDAO.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroups() throws DaoException {
        cathedraDAO.add(new Cathedra());
        scheduleDAO.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.add(new Group(TEST, 1, 1));
        }
        int expected = 5;
        int actual = groupDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeGroupById() throws DaoException {
        cathedraDAO.add(new Cathedra());
        scheduleDAO.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.add(new Group(TEST, 1, 1));
        }
        groupDAO.removeById(1);
        int expected = 4;
        int actual = groupDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroupsRelatedToCathedra() throws DaoException {
        cathedraDAO.add(new Cathedra());
        cathedraDAO.add(new Cathedra());
        scheduleDAO.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.add(new Group(TEST, 1, 1));
            groupDAO.add(new Group(TEST, 1, 2));
        }
        int expected = 5;
        int actual = groupDAO.findAllGroupsRelatedToCathedra(1).size();
        assertEquals(expected, actual);
    }
}