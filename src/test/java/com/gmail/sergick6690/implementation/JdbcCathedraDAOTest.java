package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Cathedra;
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
class JdbcCathedraDAOTest {
    private TablesCreator creator;
    private CathedraDAO cathedraDAO;
    private FacultyDAO facultyDAO;
    @Mock
    private JdbcCathedraDAO mockCathedraDAO;
    private final String TEST = "test";

    @Autowired
    public JdbcCathedraDAOTest(TablesCreator creator, CathedraDAO cathedraDAO, FacultyDAO facultyDAO) {
        this.creator = creator;
        this.cathedraDAO = cathedraDAO;
        this.facultyDAO=facultyDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddCathedra() throws DaoException {
      generateTestData();
        Cathedra expected = new Cathedra(1, TEST,1, null);
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
        Cathedra expected = new Cathedra(4, TEST,1, null);
        Cathedra actual = cathedraDAO.findById(4);
        assertEquals(expected, actual);
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
        doThrow(DaoException.class).when(mockCathedraDAO).add(new Cathedra());
        assertThrows(DaoException.class, () -> {
            mockCathedraDAO.add(new Cathedra());
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

    public void generateTestData() throws DaoException {
        facultyDAO.add(new Faculty(1,TEST, null));
        for (int i = 0; i < 5; i++) {
            cathedraDAO.add(new Cathedra(1, TEST, 1,null));
        }
    }
}