package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcScheduleDAOTest {
    private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    private TablesCreator creator = (TablesCreator) applicationContext.getBean("tablesCreator");
    private ScheduleDAO scheduleDAO = applicationContext.getBean(JdbcScheduleDAO.class);
    private static final String TEST = "test";

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }


    @Test
    void shouldAddSchedule() {
        scheduleDAO.add(new Schedule(1, TEST, null));
        Schedule expected = new Schedule(1, TEST, null);
        Schedule actual = scheduleDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindScheduleByID() throws Exception {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.add(new Schedule(1, TEST, null));
        }
        Schedule expected = new Schedule(3, TEST, null);
        Schedule actual = scheduleDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void findAllSchedules() {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.add(new Schedule());
        }
        int expected = 5;
        int actual = scheduleDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeScheduleById() {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.add(new Schedule());
        }
        scheduleDAO.removeById(1);
        int expected = 4;
        int actual = scheduleDAO.findAll().size();
        assertEquals(expected, actual);
    }
}