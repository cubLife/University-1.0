package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Cathedra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcCathedraDAOTest {
  private   AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
   private TablesCreator creator= (TablesCreator) applicationContext.getBean("tablesCreator");
    private CathedraDAO cathedraDAO =applicationContext.getBean(JdbcCathedraDAO.class);

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables();
    }

    @Test
    void shouldAddCathedra() {
        cathedraDAO.addCathedra(new Cathedra());
        int expected = 1;
        int actual = cathedraDAO.findAllCathedras().get(0).getId();
        assertEquals(expected,actual);
    }

    @Test
    void shouldRremoveCathedraById() {
        for (int i=0;i<5;i++){
            cathedraDAO.addCathedra(new Cathedra());
        }
        cathedraDAO.removeCathedraById(1);
        int expected =4;
        int actual = cathedraDAO.findAllCathedras().size();
        assertEquals(expected,actual);

    }

    @Test
    void shouldFindCathedraById() throws SQLException {
        for (int i=0;i<5;i++){
            cathedraDAO.addCathedra(new Cathedra());
        }
        int expected =4;
        int actual = cathedraDAO.findCathedraById(4).getId();
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindAllCathedras() {
        for (int i=0;i<5;i++){
            cathedraDAO.addCathedra(new Cathedra());
        }
        int expected =5;
        int actual = cathedraDAO.findAllCathedras().size();
        assertEquals(expected,actual);
    }
}