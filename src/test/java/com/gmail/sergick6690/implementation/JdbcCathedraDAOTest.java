package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Cathedra;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
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
class JdbcCathedraDAOTest {
    private TablesCreator creator;
    private CathedraDAO cathedraDAO;
    private final String TEST = "test";

    @Autowired
    public JdbcCathedraDAOTest(TablesCreator creator, CathedraDAO cathedraDAO) {
        this.creator = creator;
        this.cathedraDAO = cathedraDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddCathedra() {
        cathedraDAO.add(new Cathedra(1, TEST, null));
        Cathedra expected = new Cathedra(1, TEST, null);
        Cathedra actual = cathedraDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRremoveCathedraById() {
        for (int i = 0; i < 5; i++) {
            cathedraDAO.add(new Cathedra());
        }
        cathedraDAO.removeById(1);
        int expected = 4;
        int actual = cathedraDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindCathedraById() throws NotImplementedException {
        for (int i = 0; i < 5; i++) {
            cathedraDAO.add(new Cathedra(1, TEST, null));
        }
        Cathedra expected = new Cathedra(4, TEST, null);
        Cathedra actual = cathedraDAO.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllCathedras() {
        for (int i = 0; i < 5; i++) {
            cathedraDAO.add(new Cathedra());
        }
        int expected = 5;
        int actual = cathedraDAO.findAll().size();
        assertEquals(expected, actual);
    }
}