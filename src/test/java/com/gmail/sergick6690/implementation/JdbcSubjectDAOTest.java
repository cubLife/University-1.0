package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.*;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSubjectDAOTest {
    private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    private TablesCreator creator = (TablesCreator) applicationContext.getBean("tablesCreator");
    private ItemDAO itemDAO = applicationContext.getBean(ItemDAO.class);
    private ScheduleDAO scheduleDAO = applicationContext.getBean(JdbcScheduleDAO.class);
    private SubjectDAO subjectDAO = applicationContext.getBean(JdbcSubjectDAO.class);
    private AudienceDAO audienceDAO = applicationContext.getBean(JdbcAudienceDAO.class);
    private TeacherDAO teacherDAO = applicationContext.getBean(JdbcTeacherDAO.class);
    private static final String TEST = "Test";

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables();
    }

    @Test
    void addSubject() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.addSchedule(schedule);
        teacherDAO.addTeacher(teacher);
        subjectDAO.addSubject(new Subject(1, TEST, 1, TEST));
        int expected = 1;
        int actual = subjectDAO.findAllSubjects().get(0).getId();
        assertEquals(expected, actual);
    }

    @Test
    void findSubjectById() throws SQLException {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.addSchedule(schedule);
        teacherDAO.addTeacher(teacher);
        for (int i = 0; i < 5; i++) {
            subjectDAO.addSubject(new Subject(1, TEST, 1, TEST));
        }
        int expected = 4;
        int actual = subjectDAO.findSubjectById(4).getId();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjects() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.addSchedule(schedule);
        teacherDAO.addTeacher(teacher);
        for (int i = 0; i < 5; i++) {
            subjectDAO.addSubject(new Subject(1, TEST, 1, TEST));
        }
        int expected = 5;
        int actual = subjectDAO.findAllSubjects().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeSubjectById() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.addSchedule(schedule);
        teacherDAO.addTeacher(teacher);
        for (int i = 0; i < 5; i++) {
            subjectDAO.addSubject(new Subject(1, TEST, 1, TEST));
        }
        subjectDAO.removeSubjectById(1);
        int expected = 4;
        int actual = subjectDAO.findAllSubjects().size();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjectRelatedToAudience() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        Subject subject = new Subject(1, TEST, 1, TEST);
        Audience audience = new Audience(1, 0);
        Audience audience1 = new Audience(2, 1);
        teacherDAO.addTeacher(teacher);
        scheduleDAO.addSchedule(schedule);
        subjectDAO.addSubject(subject);
        audienceDAO.addAudience(audience);
        audienceDAO.addAudience(audience1);
        for (int i = 0; i < 5; i++) {
            itemDAO.addItem(new Item(subject, new Date(), audience, 1, schedule));
            itemDAO.addItem(new Item(subject, new Date(), audience1, 1, schedule));
        }
        int expected = 5;
        int actual = subjectDAO.findAllSubjectRelatedToAudience(2).size();
        assertEquals(expected, actual);
    }
}