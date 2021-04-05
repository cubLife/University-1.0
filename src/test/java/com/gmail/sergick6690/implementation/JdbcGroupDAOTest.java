package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.university.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcGroupDAOTest {
    private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    private TablesCreator creator = (TablesCreator) applicationContext.getBean("tablesCreator");
    private GroupDAO groupDAO = applicationContext.getBean(JdbcGroupDAO.class);
    private ScheduleDAO scheduleDAO = applicationContext.getBean(JdbcScheduleDAO.class);
    private CathedraDAO cathedraDAO = applicationContext.getBean(JdbcCathedraDAO.class);
    private static final String TEST = "test";

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddGroup() {
        cathedraDAO.add(new Cathedra());
        scheduleDAO.add(new Schedule());
        groupDAO.add(new Group(TEST, 1, 1));
        Group expected = new Group(1,TEST,null, 1, 1);
        Group actual = groupDAO.findAll().get(0);
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindGroupById() throws Exception {
        cathedraDAO.add(new Cathedra());
        scheduleDAO.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.add(new Group(TEST, 1, 1));
        }
        Group expected = new Group(2,TEST,null, 1, 1);
        Group actual = groupDAO.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroups() {
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
    void removeGroupById() {
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
    void findAllGroupsRelatedToCathedra() {
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