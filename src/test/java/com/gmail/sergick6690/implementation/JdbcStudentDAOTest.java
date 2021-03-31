package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcStudentDAOTest {
    private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    private TablesCreator creator = (TablesCreator) applicationContext.getBean("tablesCreator");
    private StudentDAO studentDAO = applicationContext.getBean(JdbcStudentDAO.class);

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables();
    }

    @Test
    void shouldAddStudent() {
        studentDAO.addStudent(new Student());
        int expected = 1;
        int actual = studentDAO.findAllStudents().get(0).getId();
        assertEquals(expected, actual);
    }

    @Test
    void findStudentById() throws SQLException {
        for (int i = 0; i < 5; i++) {
            studentDAO.addStudent(new Student());
        }
        int expected = 3;
        int actual = studentDAO.findStudentById(3).getId();
        assertEquals(expected, actual);
    }

    @Test
    void findAllStudents() {
        for (int i = 0; i < 5; i++) {
            studentDAO.addStudent(new Student());
        }
        int expected = 5;
        int actual = studentDAO.findAllStudents().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeStudentById() {
        for (int i = 0; i < 5; i++) {
            studentDAO.addStudent(new Student());
        }
        studentDAO.removeStudentById(1);
        int expected = 4;
        int actual = studentDAO.findAllStudents().size();
        assertEquals(expected, actual);
    }
}