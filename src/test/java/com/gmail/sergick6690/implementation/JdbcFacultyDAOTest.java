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
    private static final String TEST="test";

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddFaculty() {
        facultyDAO.add(new Faculty(1,TEST,null));
        Faculty expected = new Faculty(1,TEST, null);
        Faculty actual= facultyDAO.findAll().get(0);
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindFacultyById() throws Exception {
        for (int i=0;i<5;i++) {
            facultyDAO.add(new Faculty(1,TEST,null));
        }
        Faculty expected= (new Faculty(4,TEST,null));
       Faculty actual= facultyDAO.findById(4);
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindAllFaculties() {
        for (int i=0;i<5;i++) {
            facultyDAO.add(new Faculty());
        }
        int expected=5;
        int actual= facultyDAO.findAll().size();
        assertEquals(expected,actual);
    }

    @Test
    void shouldRemoveFacultyById() {
        for (int i=0;i<5;i++) {
            facultyDAO.add(new Faculty());
        }
        facultyDAO.removeById(1);
        int expected=4;
        int actual= facultyDAO.findAll().size();
        assertEquals(expected,actual);
    }
}