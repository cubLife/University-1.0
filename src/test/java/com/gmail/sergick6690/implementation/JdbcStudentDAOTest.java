package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Student;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
class JdbcStudentDAOTest {
    private TablesCreator creator;
    private StudentDAO studentDAO;
    private GroupDAO groupDAO;
    private CathedraDAO cathedraDAO;
    private ScheduleDAO scheduleDAO;
    private static final String TEST = "test";

    @Autowired
    public JdbcStudentDAOTest(TablesCreator creator, StudentDAO studentDAO, GroupDAO groupDAO, CathedraDAO cathedraDAO, ScheduleDAO scheduleDAO) {
        this.creator = creator;
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
        this.cathedraDAO = cathedraDAO;
        this.scheduleDAO = scheduleDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddStudent() {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        Student expected = Student.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build();
        Student actual = studentDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindStudentById() throws NotImplementedException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        }
        Student expected = Student.builder().id(3).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build();
        Student actual = studentDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllStudents() {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        }
        int expected = 5;
        int actual = studentDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveStudentById() {
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
    public void shouldAssignGroup() {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        studentDAO.assignGroup(1, 2);
        int expected = 2;
        int actual = studentDAO.findById(1).getGroupID();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveFromGroup() {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(2).course(0).build());
        studentDAO.removeFromGroup(1);
        int expected = 1;
        int actual = studentDAO.findById(1).getGroupID();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAssignCourse() {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(0).build());
        studentDAO.assignCourse(1, 3);
        int expected = 3;
        int actual = studentDAO.findById(1).getCourse();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldRemoveFromCourse() {
        createTestData();
        studentDAO.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).groupID(1).course(3).build());
        studentDAO.removeFromCourse(1);
        int expected = 0;
        int actual = studentDAO.findById(1).getCourse();
        assertEquals(expected, actual);
    }

    private void createTestData() {
        scheduleDAO.add(new Schedule());
        cathedraDAO.add(new Cathedra());
        groupDAO.add(new Group(TEST, 1, 1));
        groupDAO.add(new Group(TEST, 1, 1));
    }
}