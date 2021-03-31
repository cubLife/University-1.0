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
        creator.createTables();
    }

    @Test
    void shouldAddGroup() {
        cathedraDAO.addCathedra(new Cathedra());
        scheduleDAO.addSchedule(new Schedule());
        groupDAO.addGroup(new Group(TEST, 1, 1));
        int expected = 1;
        int actual = groupDAO.findAllGroups().get(0).getId();
        //  assertEquals(expected,actual);
    }

    @Test
    void shouldFindGroupById() {
        cathedraDAO.addCathedra(new Cathedra());
        scheduleDAO.addSchedule(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.addGroup(new Group(TEST, 1, 1));
        }
        int expected = 3;
        int actual = groupDAO.findAllGroups().get(2).getId();
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroups() {
        cathedraDAO.addCathedra(new Cathedra());
        scheduleDAO.addSchedule(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.addGroup(new Group(TEST, 1, 1));
        }
        int expected = 5;
        int actual = groupDAO.findAllGroups().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeGroupById() {
        cathedraDAO.addCathedra(new Cathedra());
        scheduleDAO.addSchedule(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.addGroup(new Group(TEST, 1, 1));
        }
        groupDAO.removeGroupById(1);
        int expected = 4;
        int actual = groupDAO.findAllGroups().size();
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroupsRelatedToCathedra() {
        cathedraDAO.addCathedra(new Cathedra());
        cathedraDAO.addCathedra(new Cathedra());
        scheduleDAO.addSchedule(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupDAO.addGroup(new Group(TEST, 1, 1));
            groupDAO.addGroup(new Group(TEST, 1, 2));
        }
        int expected = 5;
        int actual = groupDAO.findAllGroupsRelatedToCathedra(1).size();
        assertEquals(expected, actual);
    }
}