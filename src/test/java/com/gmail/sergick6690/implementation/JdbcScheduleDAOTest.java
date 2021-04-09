package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
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
class JdbcScheduleDAOTest {
    private TablesCreator creator;
    private ScheduleDAO scheduleDAO;
    private static final String TEST = "test";

    @Autowired
    public JdbcScheduleDAOTest(TablesCreator creator, ScheduleDAO scheduleDAO) {
        this.creator = creator;
        this.scheduleDAO = scheduleDAO;
    }

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
    void shouldFindScheduleByID() throws NotImplementedException {
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