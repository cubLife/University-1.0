package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.*;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.*;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
class JdbcSubjectDAOTest {
    private TablesCreator creator;
    private ItemDAO itemDAO;
    private ScheduleDAO scheduleDAO;
    private SubjectDAO subjectDAO;
    private AudienceDAO audienceDAO;
    private TeacherDAO teacherDAO;
    @Mock
    private JdbcSubjectDAO mockSubjectDAO;
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
    void shouldAddSubject() throws DaoException {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = Teacher.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).degree(TEST).
                scheduleId(1).subjects(null).build();
        scheduleDAO.add(schedule);
        teacherDAO.add(teacher);
        subjectDAO.add(new Subject(1, TEST, 1, TEST));
        Subject expected = new Subject(1, TEST, 1, TEST);
        Subject actual = subjectDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void findSubjectById() throws NotImplementedException, DaoException {
        generateTestData();
        Subject expected = new Subject(4, TEST, 1, TEST);
        Subject actual = subjectDAO.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjects() throws DaoException {
        generateTestData();
        int expected = 5;
        int actual = subjectDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeSubjectById() throws DaoException {
        generateTestData();
        subjectDAO.removeById(2);
        int expected = 4;
        int actual = subjectDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjectRelatedToAudience() throws DaoException {
        generateTestData();
        int expected = 5;
        int actual = subjectDAO.findAllSubjectRelatedToAudience(2).size();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAssignTeacher() throws DaoException {
        generateTestData();
        subjectDAO.assignTeacher(1, 2);
        int expected = 2;
        int actual = subjectDAO.findById(1).getTeacherId();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldRemoveTeacher() throws DaoException {
        generateTestData();
        subjectDAO.assignTeacher(1, 2);
        subjectDAO.removeTeacher(1);
        int expected = 1;
        int actual = subjectDAO.findById(1).getTeacherId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddSubjectMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockSubjectDAO).add(new Subject());
        assertThrows(DaoException.class, () -> {
            mockSubjectDAO.add(new Subject());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockSubjectDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockSubjectDAO.findById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllSubjectsMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockSubjectDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockSubjectDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveSubjectByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockSubjectDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockSubjectDAO.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllSubjectRelatedToAudienceMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockSubjectDAO).findAllSubjectRelatedToAudience(anyInt());
        assertThrows(DaoException.class, () -> {
            mockSubjectDAO.findAllSubjectRelatedToAudience(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenAssignTeacherMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockSubjectDAO).assignTeacher(anyInt(), anyInt());
        assertThrows(DaoException.class, () -> {
            mockSubjectDAO.assignTeacher(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveTeacherMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockSubjectDAO).removeTeacher(anyInt());
        assertThrows(DaoException.class, () -> {
            mockSubjectDAO.removeTeacher(anyInt());
        });
    }

    private void generateTestData() throws DaoException {
        Schedule schedule = new Schedule(1, TEST, null);
        Teacher teacher = Teacher.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).degree(TEST).
                scheduleId(1).build();
        Audience audience = new Audience(1, 0);
        Audience audience1 = new Audience(2, 1);
        Subject subject = new Subject(1, TEST, 1, TEST);
        scheduleDAO.add(schedule);
        teacherDAO.add(teacher);
        teacherDAO.add(teacher);
        audienceDAO.add(audience);
        audienceDAO.add(audience1);
        for (int i = 0; i < 5; i++) {
            subjectDAO.add(subject);
            itemDAO.add(new Item(subject, new Date(), audience, 1, schedule));
            itemDAO.add(new Item(subject, new Date(), audience1, 1, schedule));
        }
    }
}