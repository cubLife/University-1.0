package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.Schedule;
import com.gmail.sergick6690.universityModels.Subject;
import com.gmail.sergick6690.universityModels.Teacher;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    private static final String TEST = "Test";

    @Test
    void shouldAddTeacher() {
        generateTestData();
        Teacher expected = Teacher.builder().id(1).firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST).subjects(new ArrayList<>()).build();
        Teacher actual = teacherRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveTeacherById() {
        generateTestData();
        int expected = 10;
        int actual = teacherRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeacherById() throws NotImplementedException {
        generateTestData();
        Teacher expected = Teacher.builder().id(5).firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST).subjects(new ArrayList<>()).build();
        Teacher actual = teacherRepository.findById(5).get();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeacher() {
        generateTestData();
        int expected = 10;
        int actual = teacherRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeachersWithEqualDegree() {
        generateTestData();
        Long expected = 5L;
        Long actual = teacherRepository.findTeachersCountWithEqualDegree(TEST);
        assertEquals(expected, actual);
    }

    private void generateTestData() {
        scheduleRepository.save(new Schedule(TEST + 1));
        scheduleRepository.save(new Schedule(TEST + 2));
        for (int i = 0; i < 5; i++) {
            teacherRepository.save(Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST)
                    .subjects(new ArrayList<Subject>()).build());
            teacherRepository.save(Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST + 1).subjects(new ArrayList<Subject>()).build());
        }
    }
}