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

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables();
    }


    @Test
    void shouldAddSchedule() {
        scheduleDAO.addSchedule(new Schedule());
        int expected = 1;
        int actual = scheduleDAO.findAllSchedules().get(0).getId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindScheduleByID() throws SQLException {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.addSchedule(new Schedule());
        }
        int expected = 3;
        int actual = scheduleDAO.findScheduleByID(3).getId();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSchedules() {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.addSchedule(new Schedule());
        }
        int expected = 5;
        int actual = scheduleDAO.findAllSchedules().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeScheduleById() {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.addSchedule(new Schedule());
        }
        scheduleDAO.removeScheduleById(1);
        int expected = 4;
        int actual = scheduleDAO.findAllSchedules().size();
        assertEquals(expected, actual);
    }
}