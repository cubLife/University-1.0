package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.ScheduleRepository;
import com.gmail.sergick6690.Repository.TeacherRepository;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Teacher;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class JpaTeacherRepositoryTest {
    private TeacherRepository teacherRepository;
    private ScheduleRepository scheduleRepository;
    @Mock
    private JpaTeacherRepository mockTeacherRepository;
    private static final String TEST = "Test";

    @Autowired
    public JpaTeacherRepositoryTest(TeacherRepository teacherRepository, ScheduleRepository scheduleRepository) {
        this.teacherRepository = teacherRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Test
    void shouldAddTeacher() throws RepositoryException {
        generateTestData();
        Teacher expected = Teacher.builder().id(1).firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).subjects(new ArrayList<>()).build();
        Teacher actual = teacherRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveTeacherById() throws RepositoryException {
        generateTestData();
        int expected = 10;
        int actual = teacherRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeacherById() throws NotImplementedException, RepositoryException {
        generateTestData();
        Teacher expected = Teacher.builder().id(5).firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).subjects(new ArrayList<>()).build();
        Teacher actual = teacherRepository.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeacher() throws RepositoryException {
        generateTestData();
        int expected = 10;
        int actual = teacherRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllTeachersWithEqualDegree() throws RepositoryException {
        generateTestData();
        Long expected = 5L;
        Long actual = teacherRepository.findTeachersCountWithEqualDegree(TEST);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddTeacherMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockTeacherRepository).add(new Teacher());
        assertThrows(RepositoryException.class, () -> {
            mockTeacherRepository.add(new Teacher());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindTeacherByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockTeacherRepository).findById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockTeacherRepository.findById(anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllTeacherMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockTeacherRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockTeacherRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveTeacherByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockTeacherRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockTeacherRepository.removeById(anyInt());
        });
    }


    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindTeacherByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockTeacherRepository).removeById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockTeacherRepository.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllTeachersWithEqualDegreeMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockTeacherRepository).findTeachersCountWithEqualDegree(anyString());
        assertThrows(RepositoryException.class, () -> {
            mockTeacherRepository.findTeachersCountWithEqualDegree(anyString());
        });
    }

    private void generateTestData() throws RepositoryException {
        scheduleRepository.add(new Schedule());
        scheduleRepository.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            teacherRepository.add(Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST)
                    .subjects(null).build());
            teacherRepository.add(Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST + 1).subjects(null).build());
        }
    }
}