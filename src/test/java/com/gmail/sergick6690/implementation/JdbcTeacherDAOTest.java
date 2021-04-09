package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Teacher;
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
        teacherDAO.add(Teacher.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).degree(TEST).
                schedule(schedule).subjects(null).build());
        Teacher expected = Teacher.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).degree(TEST).
                schedule(null).subjects(null).build();
        Teacher actual = teacherDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveTeacherById() {
      generateTestData();
        int expected = 10;
        int actual = teacherDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeacherById() throws NotImplementedException {
        generateTestData();
        Teacher expected = Teacher.builder().id(5).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).degree(TEST).
                schedule(null).subjects(null).build();
        Teacher actual = teacherDAO.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeacher() {
        generateTestData();
        int expected = 10;
        int actual = teacherDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeachersWithEqualDegree() {
        generateTestData();
        int expected = 5;
        int actual = teacherDAO.findTeachersCountWithEqualDegree(TEST);
        assertEquals(expected, actual);
    }

    private void generateTestData() {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.add(Teacher.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).degree(TEST).
                    schedule(schedule).subjects(null).build());
            teacherDAO.add(Teacher.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).degree(TEST + 1).
                    schedule(schedule).subjects(null).build());
        }
    }
}