package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.university.Audience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@Component
class JdbcAudienceDAOTest {
    TablesCreator creator;
    AudienceDAO audienceDAO;

    @Autowired
    public JdbcAudienceDAOTest(TablesCreator creator, AudienceDAO audienceDAO) {
        this.creator = creator;
        this.audienceDAO = audienceDAO;
    }

    @BeforeEach
    void createTables() throws IOException, URISyntaxException {
        creator.createTables("Script.sql");
    }

    @Test
    void shouldAddAudience() {
        audienceDAO.add(new Audience());
        Audience expected = new Audience(1, 0);
        Audience actual = audienceDAO.findAll().get(0);
        assertEquals(expected, actual);

    }

    @Test
    void shouldFindAudienceById() throws Exception {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.add(new Audience());
        }
        Audience expected = new Audience(3, 0);
        Audience actual = audienceDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllAudience() {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.add(new Audience());
        }
        int expected = 6;
        int actual = audienceDAO.findAll().size();
        assertEquals(expected, actual);
    }


    @Test
    void ShouldRemoveAudienceById() {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.add(new Audience());
        }
        audienceDAO.removeById(1);
        int expected = 5;
        int actual = audienceDAO.findAll().size();
        assertEquals(expected, actual);
    }
}