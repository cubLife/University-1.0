package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Audience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcAudienceDAOTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    TablesCreator creator = (TablesCreator) applicationContext.getBean("tablesCreator");
    AudienceDAO audienceDAO = applicationContext.getBean(JdbcAudienceDAO.class);

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables();
    }

    @Test
    void shouldAddAudience() {
        audienceDAO.addAudience(new Audience());
        int expected = 1;
        int actual = audienceDAO.findAllAudience().get(0).getId();
        assertEquals(expected, actual);

    }

    @Test
    void shouldFindAudienceById() {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.addAudience(new Audience());
        }
        int expected = 4;
        int actual = audienceDAO.findAllAudience().get(3).getId();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllAudience() {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.addAudience(new Audience());
        }
        int expected = 6;
        int actual = audienceDAO.findAllAudience().size();
        assertEquals(expected, actual);
    }


    @Test
    void ShouldRemoveAudienceById() {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.addAudience(new Audience());
        }
        audienceDAO.removeAudienceById(1);
        int expected = 5;
        int actual = audienceDAO.findAllAudience().size();
        assertEquals(expected, actual);
    }
}