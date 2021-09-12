package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.*;
import com.gmail.sergick6690.spring.SpringConfig;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class JdbcSubjectDAOTest {
    private ItemDAO itemDAO;
    private ScheduleDAO scheduleDAO;
    private SubjectDAO subjectDAO;
    private AudienceDAO audienceDAO;
    private TeacherDAO teacherDAO;
    @Mock
    private JdbcSubjectDAO mockSubjectDAO;
    private static final String TEST = "Test";

    @Autowired
    public JdbcSubjectDAOTest(ItemDAO itemDAO, ScheduleDAO scheduleDAO, SubjectDAO subjectDAO,
                              AudienceDAO audienceDAO, TeacherDAO teacherDAO) {
        this.itemDAO = itemDAO;
        this.scheduleDAO = scheduleDAO;
        this.subjectDAO = subjectDAO;
        this.audienceDAO = audienceDAO;
        this.teacherDAO = teacherDAO;
    }

    @Test
    void shouldAddSubject() throws DaoException {
        generateTestData();
        Subject expected = new Subject(1, TEST, teacherDAO.findById(1), TEST);
        Subject actual = subjectDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void findSubjectById() throws NotImplementedException, DaoException {
        generateTestData();
        Subject expected = new Subject(4, TEST, teacherDAO.findById(1), TEST);
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

    private void generateTestData() throws DaoException {
        scheduleDAO.add(new Schedule(TEST));
        Schedule schedule = scheduleDAO.findById(1);
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).build();
        teacherDAO.add(teacher);
        Audience audience = new Audience();
        audience.setNumber(0);
        Audience audience1 = new Audience();
        audience1.setNumber(1);
        audienceDAO.add(audience);
        audienceDAO.add(audience1);
        for (int i = 0; i < 5; i++) {
            subjectDAO.add(new Subject(TEST, teacherDAO.findById(1), TEST));
            itemDAO.add(new Item(subjectDAO.findById(1), TEST, 1, audienceDAO.findById(1), 1, scheduleDAO.findById(1)));
            itemDAO.add(new Item(subjectDAO.findById(1), TEST, 1, audienceDAO.findById(2), 1, scheduleDAO.findById(1)));
        }
    }
}