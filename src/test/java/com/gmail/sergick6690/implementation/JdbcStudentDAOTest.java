package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Student;
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
class JdbcStudentDAOTest {
    private TablesCreator creator;
    private StudentDAO studentDAO;
    private static final String TEST = "test";

    @Autowired
    public JdbcStudentDAOTest(TablesCreator creator, StudentDAO studentDAO) {
        this.creator = creator;
        this.studentDAO = studentDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddStudent() {
        studentDAO.add(new Student(1, TEST, TEST, TEST, 0, 0));
        Student expected = new Student(1, TEST, TEST, TEST, 0, 0);
        Student actual = studentDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void findStudentById() throws Exception {
        for (int i = 0; i < 5; i++) {
            studentDAO.add(new Student(1, TEST, TEST, TEST, 0, 0));
        }
        Student expected = new Student(3, TEST, TEST, TEST, 0, 0);
        Student actual = studentDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void findAllStudents() {
        for (int i = 0; i < 5; i++) {
            studentDAO.add(new Student());
        }
        int expected = 5;
        int actual = studentDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeStudentById() {
        for (int i = 0; i < 5; i++) {
            studentDAO.add(new Student());
        }
        studentDAO.removeById(1);
        int expected = 4;
        int actual = studentDAO.findAll().size();
        assertEquals(expected, actual);
    }
}