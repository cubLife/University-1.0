package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Teacher;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
class JdbcTeacherDAOTest {
    private TablesCreator creator;
    private TeacherDAO teacherDAO;
    private ScheduleDAO scheduleDAO;
    @Mock
    private JdbcTeacherDAO mockTeacherDAO;
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
    void shouldAddTeacher() throws DaoException {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        teacherDAO.add(Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).
                scheduleId(1).subjects(null).build());
        Teacher expected = Teacher.builder().id(1).firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).
                scheduleId(1).subjects(null).build();
        Teacher actual = teacherDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveTeacherById() throws DaoException {
        generateTestData();
        int expected = 10;
        int actual = teacherDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeacherById() throws NotImplementedException, DaoException {
        generateTestData();
        Teacher expected = Teacher.builder().id(5).firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).
                scheduleId(1).subjects(null).build();
        Teacher actual = teacherDAO.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeacher() throws DaoException {
        generateTestData();
        int expected = 10;
        int actual = teacherDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeachersWithEqualDegree() throws DaoException {
        generateTestData();
        int expected = 5;
        int actual = teacherDAO.findTeachersCountWithEqualDegree(TEST);
        assertEquals(expected, actual);
    }

    @Test
    public void removeSchedule() throws DaoException {
        generateTestData();
        teacherDAO.assignSchedule(1, 2);
        teacherDAO.removeSchedule(1);
        int expected = 1;
        int actual = teacherDAO.findById(1).getScheduleId();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAssignSchedule() throws DaoException {
        generateTestData();
        teacherDAO.assignSchedule(1, 2);
        int expected = 2;
        int actual = teacherDAO.findById(1).getScheduleId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddTeacherMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockTeacherDAO).add(new Teacher());
        assertThrows(DaoException.class, () -> {
            mockTeacherDAO.add(new Teacher());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindTeacherByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockTeacherDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockTeacherDAO.findById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllTeacherMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockTeacherDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockTeacherDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveTeacherByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockTeacherDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockTeacherDAO.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllTeachersWithEqualDegreeMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockTeacherDAO).findTeachersCountWithEqualDegree(anyString());
        assertThrows(DaoException.class, () -> {
            mockTeacherDAO.findTeachersCountWithEqualDegree(anyString());
        });
    }

    @Test
    void shouldThrowDaoExceptionAssignScheduleWhenMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockTeacherDAO).assignSchedule(anyInt(), anyInt());
        assertThrows(DaoException.class, () -> {
            mockTeacherDAO.assignSchedule(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionRemoveScheduleWhenMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockTeacherDAO).removeSchedule(anyInt());
        assertThrows(DaoException.class, () -> {
            mockTeacherDAO.removeSchedule(anyInt());
        });
    }

    private void generateTestData() throws DaoException {
        Schedule schedule = new Schedule(1, TEST, null);
        scheduleDAO.add(schedule);
        scheduleDAO.add(schedule);
        for (int i = 0; i < 5; i++) {
            teacherDAO.add(Teacher.builder().id(1).firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).
                    scheduleId(1).subjects(null).build());
            teacherDAO.add(Teacher.builder().id(1).firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST + 1).
                    scheduleId(1).subjects(null).build());
        }
    }
}