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

class JdbcItemDAOTest {
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
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddItem() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        Subject subject = new Subject(1, TEST, 1, TEST);
        Audience audience = new Audience(1, 0);
        teacherDAO.add(teacher);
        scheduleDAO.add(schedule);
        subjectDAO.add(subject);
        audienceDAO.add(audience);
        itemDAO.add(new Item(subject, null, audience, 1, schedule));
        Item expected = new Item(1,null, null, null, 1, null);
        Item actual = itemDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindItemById() throws Exception {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        Subject subject = new Subject(1, TEST, 1, TEST);
        Audience audience = new Audience(1, 0);
        teacherDAO.add(teacher);
        scheduleDAO.add(schedule);
        subjectDAO.add(subject);
        audienceDAO.add(audience);
        for (int i = 0; i < 5; i++) {
            itemDAO.add(new Item(subject, null, audience, 1, schedule));
        }
        Item expected = new Item(3,null, null, null, 1, null);
        Item actual = itemDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllItems() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        Subject subject = new Subject(1, TEST, 1, TEST);
        Audience audience = new Audience(1, 0);
        teacherDAO.add(teacher);
        scheduleDAO.add(schedule);
        subjectDAO.add(subject);
        audienceDAO.add(audience);
        for (int i = 0; i < 5; i++) {
            itemDAO.add(new Item(subject, new Date(), audience, 1, schedule));
        }
        int expected = 5;
        int actual = itemDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveItemsById() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        Subject subject = new Subject(1, TEST, 1, TEST);
        Audience audience = new Audience(1, 0);
        teacherDAO.add(teacher);
        scheduleDAO.add(schedule);
        subjectDAO.add(subject);
        audienceDAO.add(audience);
        for (int i = 0; i < 5; i++) {
            itemDAO.add(new Item(subject, new Date(), audience, 1, schedule));
        }
        itemDAO.removeById(1);
        int expected = 4;
        int actual = itemDAO.findAll().size();
        assertEquals(expected, actual);
    }
}