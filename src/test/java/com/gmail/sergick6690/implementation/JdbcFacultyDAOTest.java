package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Faculty;
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
class JdbcFacultyDAOTest {
    private TablesCreator creator;
    private FacultyDAO facultyDAO;
    private static final String TEST = "test";

    @Autowired
    public JdbcFacultyDAOTest(TablesCreator creator, FacultyDAO facultyDAO) {
        this.creator = creator;
        this.facultyDAO = facultyDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddFaculty() {
        facultyDAO.add(new Faculty(1, TEST, null));
        Faculty expected = new Faculty(1, TEST, null);
        Faculty actual = facultyDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindFacultyById() throws Exception {
        for (int i = 0; i < 5; i++) {
            facultyDAO.add(new Faculty(1, TEST, null));
        }
        Faculty expected = (new Faculty(4, TEST, null));
        Faculty actual = facultyDAO.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllFaculties() {
        for (int i = 0; i < 5; i++) {
            facultyDAO.add(new Faculty());
        }
        int expected = 5;
        int actual = facultyDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveFacultyById() {
        for (int i = 0; i < 5; i++) {
            facultyDAO.add(new Faculty());
        }
        facultyDAO.removeById(1);
        int expected = 4;
        int actual = facultyDAO.findAll().size();
        assertEquals(expected, actual);
    }
}