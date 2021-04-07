package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.*;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
class JdbcItemDAOTest {
    private TablesCreator creator;
    private ItemDAO itemDAO;
    private ScheduleDAO scheduleDAO;
    private SubjectDAO subjectDAO;
    private AudienceDAO audienceDAO;
    private TeacherDAO teacherDAO;
    private static final String TEST = "Test";

    @Autowired
    public JdbcItemDAOTest(TablesCreator creator, ItemDAO itemDAO, ScheduleDAO scheduleDAO,
                           SubjectDAO subjectDAO, AudienceDAO audienceDAO, TeacherDAO teacherDAO) {
        this.creator = creator;
        this.itemDAO = itemDAO;
        this.scheduleDAO = scheduleDAO;
        this.subjectDAO = subjectDAO;
        this.audienceDAO = audienceDAO;
        this.teacherDAO = teacherDAO;
    }

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
        Item expected = new Item(1, null, null, null, 1, null);
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
        Item expected = new Item(3, null, null, null, 1, null);
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