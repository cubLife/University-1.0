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
class JdbcSubjectDAOTest {
    private TablesCreator creator;
    private ItemDAO itemDAO;
    private ScheduleDAO scheduleDAO;
    private SubjectDAO subjectDAO;
    private AudienceDAO audienceDAO;
    private TeacherDAO teacherDAO;
    private static final String TEST = "Test";

    @Autowired
    public JdbcSubjectDAOTest(TablesCreator creator, ItemDAO itemDAO, ScheduleDAO scheduleDAO, SubjectDAO subjectDAO,
                              AudienceDAO audienceDAO, TeacherDAO teacherDAO) {
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
    void addSubject() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.add(schedule);
        teacherDAO.add(teacher);
        subjectDAO.add(new Subject(1, TEST, 1, TEST));
        Subject expected = new Subject(1, TEST, 1, TEST);
        Subject actual = subjectDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void findSubjectById() throws Exception {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.add(schedule);
        teacherDAO.add(teacher);
        for (int i = 0; i < 5; i++) {
            subjectDAO.add(new Subject(1, TEST, 1, TEST));
        }
        Subject expected = new Subject(4, TEST, 1, TEST);
        Subject actual = subjectDAO.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjects() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.add(schedule);
        teacherDAO.add(teacher);
        for (int i = 0; i < 5; i++) {
            subjectDAO.add(new Subject(1, TEST, 1, TEST));
        }
        int expected = 5;
        int actual = subjectDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeSubjectById() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        scheduleDAO.add(schedule);
        teacherDAO.add(teacher);
        for (int i = 0; i < 5; i++) {
            subjectDAO.add(new Subject(1, TEST, 1, TEST));
        }
        subjectDAO.removeById(1);
        int expected = 4;
        int actual = subjectDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjectRelatedToAudience() {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = new Teacher(1, TEST, TEST, TEST, 0, TEST, schedule, null);
        Subject subject = new Subject(1, TEST, 1, TEST);
        Audience audience = new Audience(1, 0);
        Audience audience1 = new Audience(2, 1);
        teacherDAO.add(teacher);
        scheduleDAO.add(schedule);
        subjectDAO.add(subject);
        audienceDAO.add(audience);
        audienceDAO.add(audience1);
        for (int i = 0; i < 5; i++) {
            itemDAO.add(new Item(subject, new Date(), audience, 1, schedule));
            itemDAO.add(new Item(subject, new Date(), audience1, 1, schedule));
        }
        int expected = 5;
        int actual = subjectDAO.findAllSubjectRelatedToAudience(2).size();
        assertEquals(expected, actual);
    }
}