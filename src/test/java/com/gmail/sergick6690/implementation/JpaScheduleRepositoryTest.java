package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.ScheduleRepository;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.spring.SpringConfig;
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
class JpaScheduleRepositoryTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Mock
    private JpaScheduleRepository mockScheduleRepository;
    private static final String TEST = "test";

    @Test
    void shouldAddSchedule() throws RepositoryException {
        scheduleRepository.add(new Schedule(TEST));
        Schedule expected = new Schedule(TEST);
        expected.setId(1);
        Schedule actual = scheduleRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindScheduleByID() throws NotImplementedException, RepositoryException {
        for (int i = 0; i < 5; i++) {
            scheduleRepository.add(new Schedule(TEST));
        }
        Schedule expected = new Schedule(3, TEST, null);
        Schedule actual = scheduleRepository.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void findAllSchedules() throws RepositoryException {
        for (int i = 0; i < 5; i++) {
            scheduleRepository.add(new Schedule());
        }
        int expected = 5;
        int actual = scheduleRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeScheduleById() throws RepositoryException {
        for (int i = 0; i < 5; i++) {
            scheduleRepository.add(new Schedule());
        }
        scheduleRepository.removeById(1);
        int expected = 4;
        int actual = scheduleRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddScheduleMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockScheduleRepository).add(new Schedule());
        assertThrows(RepositoryException.class, () -> {
            mockScheduleRepository.add(new Schedule());
        });
    }

    @Test
    void shouldThrowWhenFindScheduleByIdMethodCall() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> {
            scheduleRepository.findById(1);
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindScheduleByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockScheduleRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockScheduleRepository.findById(anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllScheduleMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockScheduleRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockScheduleRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveScheduleByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockScheduleRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockScheduleRepository.removeById(anyInt());
        });
    }
}