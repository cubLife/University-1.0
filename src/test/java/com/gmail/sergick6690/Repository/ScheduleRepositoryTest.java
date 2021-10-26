package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Schedule;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class ScheduleRepositoryTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    private static final String TEST = "test1";

    @Test
    void shouldAddSchedule() {
        scheduleRepository.save(new Schedule(TEST));
        Schedule expected = new Schedule(TEST);
        expected.setId(1);
        Schedule actual = scheduleRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindScheduleByID() throws NotImplementedException {
        for (int i = 0; i < 5; i++) {
            scheduleRepository.save(new Schedule(TEST));
        }
        Schedule expected = new Schedule(3, TEST, null);
        Schedule actual = scheduleRepository.findById(3).get();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSchedules() {
        for (int i = 0; i < 5; i++) {
            scheduleRepository.save(new Schedule(TEST));
        }
        int expected = 5;
        int actual = scheduleRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeScheduleById() {
        for (int i = 0; i < 5; i++) {
            scheduleRepository.save(new Schedule(TEST));
        }
        Schedule schedule = scheduleRepository.findById(1).get();
        scheduleRepository.delete(schedule);
        int expected = 4;
        int actual = scheduleRepository.findAll().size();
        assertEquals(expected, actual);
    }
}