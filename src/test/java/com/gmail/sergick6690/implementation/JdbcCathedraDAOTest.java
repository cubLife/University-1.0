package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Faculty;
import com.gmail.sergick6690.university.Group;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JdbcCathedraDAOTest {
    private CathedraDAO cathedraDAO;
    private FacultyDAO facultyDAO;
    @Mock
    private JdbcCathedraDAO mockCathedraDAO;
    private final String TEST = "test";

    @Autowired
    public JdbcCathedraDAOTest(CathedraDAO cathedraDAO, FacultyDAO facultyDAO) {
        this.cathedraDAO = cathedraDAO;
        this.facultyDAO = facultyDAO;
    }

    @Test
    void shouldAddCathedra() throws DaoException {
        generateTestData();
        Cathedra expected = new Cathedra(1, TEST);
        Cathedra actual = cathedraDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRremoveCathedraById() throws DaoException {
        generateTestData();
        cathedraDAO.removeById(1);
        int expected = 4;
        int actual = cathedraDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindCathedraById() throws NotImplementedException, DaoException {
        generateTestData();
        Cathedra expected = new Cathedra(4, TEST);
        Cathedra actual = cathedraDAO.findById(4);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void shouldFindAllCathedras() throws DaoException {
        generateTestData();
        int expected = 5;
        int actual = cathedraDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddCathedraMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockCathedraDAO).add(new Cathedra(1, TEST, new Faculty(), null));
        assertThrows(DaoException.class, () -> {
            mockCathedraDAO.add(new Cathedra(1, TEST, new Faculty(), null));
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockCathedraDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockCathedraDAO.findById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockCathedraDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockCathedraDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockCathedraDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockCathedraDAO.removeById(0);
        });
    }

    private void generateTestData() throws DaoException {
        facultyDAO.add(new Faculty());
        Faculty faculty = facultyDAO.findById(1);
        for (int i = 0; i < 5; i++) {
            cathedraDAO.add(new Cathedra(TEST, faculty));
        }
    }
}