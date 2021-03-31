package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTeacherDAOTest {
    private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    private TablesCreator creator = (TablesCreator) applicationContext.getBean("tablesCreator");
    private TeacherDAO teacherDAO = applicationContext.getBean(JdbcTeacherDAO.class);
    private ScheduleDAO scheduleDAO = applicationContext.getBean(JdbcScheduleDAO.class);
    private static final String TEST = "Test";

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables();
    }

    @Test
    void shouldAddTeacher() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.addSchedule(schedule);
        teacherDAO.addTeacher(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        int expected = 1;
        int actual = teacherDAO.findAllTeacher().get(0).getId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveTeacherById() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.addSchedule(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.addTeacher(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        }
        int expected = 5;
        int actual = teacherDAO.findAllTeacher().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeacherById() throws SQLException {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.addSchedule(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.addTeacher(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        }
        int expected = 4;
        int actual = teacherDAO.findTeacherById(4).getId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeacher() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.addSchedule(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.addTeacher(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
        }
        int expected = 5;
        int actual = teacherDAO.findAllTeacher().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeachersWithEqualDegree() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.addSchedule(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.addTeacher(new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null));
            teacherDAO.addTeacher(new Teacher(1, TEST, TEST, TEST, 0, "", schedule, null));
        }
        int expected = 5;
        int actual = teacherDAO.findTeachersCountWithEqualDegree(TEST);
        assertEquals(expected, actual);
    }
}