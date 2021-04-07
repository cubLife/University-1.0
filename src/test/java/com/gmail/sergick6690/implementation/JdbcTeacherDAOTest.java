package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Teacher;
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
class JdbcTeacherDAOTest {
    private TablesCreator creator;
    private TeacherDAO teacherDAO;
    private ScheduleDAO scheduleDAO;
    private static final String TEST = "Test";

    @Autowired
    public JdbcTeacherDAOTest(TablesCreator creator, TeacherDAO teacherDAO, ScheduleDAO scheduleDAO) {
        this.creator = creator;
        this.teacherDAO = teacherDAO;
        this.scheduleDAO = scheduleDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddTeacher() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        teacherDAO.add(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        Teacher expected = new Teacher(1, TEST, TEST, TEST, 0, TEST, null, null);
        Teacher actual = teacherDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveTeacherById() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.add(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        }
        int expected = 5;
        int actual = teacherDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeacherById() throws Exception {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.add(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        }
        Teacher expected = new Teacher(4, TEST, TEST, TEST, 0, TEST, null, null);
        Teacher actual = teacherDAO.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeacher() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.add(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        }
        int expected = 5;
        int actual = teacherDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeachersWithEqualDegree() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.add(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
            teacherDAO.add(new Teacher(1, TEST, TEST, TEST, 0, "", schedule, null));
        }
        int expected = 5;
        int actual = teacherDAO.findTeachersCountWithEqualDegree(TEST);
        assertEquals(expected, actual);
    }
}