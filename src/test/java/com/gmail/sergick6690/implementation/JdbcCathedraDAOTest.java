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
    private  final String TEST="test";

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddCathedra() {
        cathedraDAO.add(new Cathedra(1,TEST,null));
        Cathedra expected = new Cathedra(1,TEST,null);
        Cathedra actual = cathedraDAO.findAll().get(0);
        assertEquals(expected,actual);
    }

    @Test
    void shouldRremoveCathedraById() {
        for (int i=0;i<5;i++){
            cathedraDAO.add(new Cathedra());
        }
        cathedraDAO.removeById(1);
        int expected =4;
        int actual = cathedraDAO.findAll().size();
        assertEquals(expected,actual);

    }

    @Test
    void shouldFindCathedraById() throws Exception {
        for (int i=0;i<5;i++){
            cathedraDAO.add(new Cathedra(1,TEST,null));
        }
        Cathedra expected = new Cathedra(4,TEST,null);
        Cathedra actual = cathedraDAO.findById(4);
        assertEquals(expected,actual);
    }

    @Test
    void shouldFindAllCathedras() {
        for (int i=0;i<5;i++){
            cathedraDAO.add(new Cathedra());
        }
        int expected =5;
        int actual = cathedraDAO.findAll().size();
        assertEquals(expected,actual);
    }
}