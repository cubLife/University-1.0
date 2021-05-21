package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Faculty;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
class JdbcFacultyDAOTest {
    private TablesCreator creator;
    private FacultyDAO facultyDAO;
    @Mock
    private JdbcFacultyDAO mockJdbcFacultyDAO;
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
    void shouldAddFaculty() throws DaoException {
        facultyDAO.add(new Faculty(1, TEST, null));
        Faculty expected = new Faculty(1, TEST, null);
        Faculty actual = facultyDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindFacultyById() throws NotImplementedException, DaoException {
        for (int i = 0; i < 5; i++) {
            facultyDAO.add(new Faculty(1, TEST, null));
        }
        Faculty expected = (new Faculty(4, TEST, null));
        Faculty actual = facultyDAO.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllFaculties() throws DaoException {
        for (int i = 0; i < 5; i++) {
            facultyDAO.add(new Faculty());
        }
        int expected = 5;
        int actual = facultyDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveFacultyById() throws DaoException {
        for (int i = 0; i < 5; i++) {
            facultyDAO.add(new Faculty());
        }
        facultyDAO.removeById(1);
        int expected = 4;
        int actual = facultyDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddFacultyMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcFacultyDAO).add(new Faculty());
        assertThrows(DaoException.class, () -> {
            mockJdbcFacultyDAO.add(new Faculty());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcFacultyDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockJdbcFacultyDAO.findById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcFacultyDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockJdbcFacultyDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveByIdFacultyMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockJdbcFacultyDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockJdbcFacultyDAO.removeById(0);
        });
    }
}