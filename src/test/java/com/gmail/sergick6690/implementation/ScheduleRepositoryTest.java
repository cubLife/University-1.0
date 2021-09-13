package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.TablesCreator;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Schedule;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class ScheduleRepositoryTest {
    private ScheduleDAO scheduleDAO;
    @Mock
    private ScheduleRepository mockScheduleRepository;
    private static final String TEST = "test";

    @Autowired
    public ScheduleRepositoryTest(TablesCreator creator, ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    @Test
    void shouldAddSchedule() throws DaoException {
        scheduleDAO.add(new Schedule(TEST));
        Schedule expected = new Schedule(TEST);
        expected.setId(1);
        Schedule actual = scheduleDAO.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindScheduleByID() throws NotImplementedException, DaoException {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.add(new Schedule(TEST));
        }
        Schedule expected = new Schedule(3, TEST, null);
        Schedule actual = scheduleDAO.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void findAllSchedules() throws DaoException {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.add(new Schedule());
        }
        int expected = 5;
        int actual = scheduleDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeScheduleById() throws DaoException {
        for (int i = 0; i < 5; i++) {
            scheduleDAO.add(new Schedule());
        }
        scheduleDAO.removeById(1);
        int expected = 4;
        int actual = scheduleDAO.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowDaoExceptionWhenAddScheduleMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockScheduleRepository).add(new Schedule());
        assertThrows(DaoException.class, () -> {
            mockScheduleRepository.add(new Schedule());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindScheduleByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockScheduleRepository).findById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockScheduleRepository.findById(anyInt());
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindScheduleByIdMethodCall() throws DaoException {
        doThrow(IllegalArgumentException.class).when(mockScheduleRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockScheduleRepository.findById(anyInt());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllScheduleMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockScheduleRepository).findAll();
        assertThrows(DaoException.class, () -> {
            mockScheduleRepository.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveScheduleByIdMethodCall() throws DaoException {
        doThrow(DaoException.class).when(mockScheduleRepository).removeById(anyInt());
        assertThrows(DaoException.class, () -> {
            mockScheduleRepository.removeById(anyInt());
        });
    }
}