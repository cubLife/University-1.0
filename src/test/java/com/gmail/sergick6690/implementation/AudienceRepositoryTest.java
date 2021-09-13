package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Audience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AudienceRepositoryTest {

    private AudienceDAO audienceDAO;
    @Mock
    private AudienceDAO mockAudienceDAO;

    @Autowired
    public AudienceRepositoryTest(AudienceDAO audienceDAO) {
        this.audienceDAO = audienceDAO;
    }


    @Test
    void shouldAddAudience() throws DaoException {
        audienceDAO.add(new Audience());
        Audience expected = new Audience(1, 0);
        Audience actual = audienceDAO.findAll().get(0);
        System.out.println(audienceDAO.findAll());
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAudienceById() throws DaoException {
        for (int i = 0; i < 5; i++) {
            audienceDAO.add(new Audience());
        }
        Audience expected = new Audience(3, 0);
        Audience actual = audienceDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllAudience() throws DaoException {
        for (int i = 0; i < 5; i++) {
            audienceDAO.add(new Audience());
        }
        int expected = 5;
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
    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindAudienceByIdCall() throws DaoException {
        doThrow(IllegalArgumentException.class).when(mockAudienceDAO).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockAudienceDAO.findById(0);
        });
    }
}