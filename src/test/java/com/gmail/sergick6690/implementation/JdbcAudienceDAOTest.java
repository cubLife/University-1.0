package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Audience;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
class JdbcAudienceDAOTest {
    private TablesCreator creator;
    private AudienceDAO audienceDAO;
    @Mock
    private JdbcAudienceDAO mockAudienceDAO;

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
    void shouldAddAudience() throws DaoException {
        audienceDAO.add(new Audience());
        Audience expected = new Audience(1, 0);
        Audience actual = audienceDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAudienceById() throws DaoException {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.add(new Audience());
        }
        Audience expected = new Audience(3, 0);
        Audience actual = audienceDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllAudience() throws DaoException {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.add(new Audience());
        }
        int expected = 6;
        int actual = audienceDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void ShouldRemoveAudienceById() throws DaoException {
        for (int i = 0; i <= 5; i++) {
            audienceDAO.add(new Audience());
        }
        audienceDAO.removeById(1);
        int expected = 5;
        int actual = audienceDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddAudienceMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockAudienceDAO).add(new Audience());
        assertThrows(DaoException.class, () -> {
            mockAudienceDAO.add(new Audience());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAudienceByIdCall() throws DaoException {
        doThrow(DaoException.class).when(mockAudienceDAO).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockAudienceDAO.findById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllAudienceCall() throws DaoException {
        doThrow(DaoException.class).when(mockAudienceDAO).findAll();
        assertThrows(DaoException.class, () -> {
            mockAudienceDAO.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveAudienceByIdeCall() throws DaoException {
        doThrow(DaoException.class).when(mockAudienceDAO).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockAudienceDAO.removeById(0);
        });
    }
}