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
class JdbcStudentDAOTest {
    private TablesCreator creator;
    private StudentDAO studentDAO;
    private GroupDAO groupDAO;
    private CathedraDAO cathedraDAO;
    private ScheduleDAO scheduleDAO;
    private FacultyDAO facultyDAO;
    @Mock
    private JdbcStudentDAO mockStudentDAO;
    private static final String TEST = "test";

    @Autowired
    public JdbcStudentDAOTest(TablesCreator creator, StudentDAO studentDAO, GroupDAO groupDAO, CathedraDAO cathedraDAO, ScheduleDAO scheduleDAO, FacultyDAO facultyDAO) {
        this.creator = creator;
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
        this.cathedraDAO = cathedraDAO;
        this.scheduleDAO = scheduleDAO;
        this.facultyDAO = facultyDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddStudent() throws DaoException {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        Student expected = Student.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build();
        Student actual = studentDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindStudentById() throws NotImplementedException, DaoException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        }
        Student expected = Student.builder().id(3).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build();
        Student actual = studentDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllStudents() throws DaoException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        }
        int expected = 5;
        int actual = studentDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveStudentById() throws DaoException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        }
        studentDAO.removeById(1);
        int expected = 4;
        int actual = studentDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAssignGroup() throws DaoException {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        studentDAO.assignGroup(1, 2);
        int expected = 2;
        int actual = studentDAO.findById(1).getGroupId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveFromGroup() throws DaoException {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(2).course(0).build());
        studentDAO.removeFromGroup(1);
        int expected = 1;
        int actual = studentDAO.findById(1).getGroupId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAssignCourse() throws DaoException {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        studentDAO.assignCourse(1, 3);
        int expected = 3;
        int actual = studentDAO.findById(1).getCourse();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldRemoveFromCourse() throws DaoException {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(3).build());
        studentDAO.removeFromCourse(1);
        int expected = 0;
        int actual = studentDAO.findById(1).getCourse();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddStudentMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).add(new Student());
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.add(new Student());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindStudentByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.findById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllStudentMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveStudentByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenAssignCourseMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).assignCourse(anyInt(), anyInt());
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.assignCourse(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenAssignGroupMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).assignGroup(anyInt(), anyInt());
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.assignGroup(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveFromCourseMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).removeFromCourse(anyInt());
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.removeFromCourse(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveFromGroupMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockStudentDAO).removeFromGroup(anyInt());
        assertThrows(DaoException.class, () -> {
            mockStudentDAO.removeFromGroup(anyInt());
        });
    }

    private void createTestData() throws DaoException {
        scheduleDAO.add(new Schedule());
        facultyDAO.add(new Faculty(1,TEST, null));
        cathedraDAO.add(new Cathedra(1, TEST, 1,null));
        groupDAO.add(new Group(TEST, 1, 1));
        groupDAO.add(new Group(TEST, 1, 1));
    }
}