package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Faculty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcFacultyDAOTest {
    private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    private TablesCreator creator= (TablesCreator) applicationContext.getBean("tablesCreator");
    private FacultyDAO facultyDAO =applicationContext.getBean(JdbcFacultyDAO.class);

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables();
    }

    @Test
    void shouldAddFaculty() {
        facultyDAO.addFaculty(new Faculty());
        int expected=1;
        int actual= facultyDAO.findAllFaculties().get(0).getId();
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindFacultyById() throws SQLException {
        for (int i=0;i<5;i++) {
            facultyDAO.addFaculty(new Faculty());
        }
        int expected=4;
       int actual= facultyDAO.findFacultyById(4).getId();
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindAllFaculties() {
        for (int i=0;i<5;i++) {
            facultyDAO.addFaculty(new Faculty());
        }
        int expected=5;
        int actual= facultyDAO.findAllFaculties().size();
        assertEquals(expected,actual);
    }

    @Test
    void shouldRemoveFacultyById() {
        for (int i=0;i<5;i++) {
            facultyDAO.addFaculty(new Faculty());
        }
        facultyDAO.removeFacultyById(1);
        int expected=4;
        int actual= facultyDAO.findAllFaculties().size();
        assertEquals(expected,actual);
    }
}