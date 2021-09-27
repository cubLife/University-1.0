package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Faculty;
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
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FacultyRepositoryTest {
    @Autowired
    private FacultyRepository facultyRepository;
    private static final String TEST = "test";

    @Test
    void shouldAddFaculty() {
        generateTestData();
        Faculty expected = new Faculty(1, "test");
        Faculty actual = facultyRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindFacultyById() {
        generateTestData();
        Faculty expected = (new Faculty(4, TEST));
        Faculty actual = facultyRepository.findById(4).get();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllFaculties() {
        generateTestData();
        int expected = 5;
        int actual = facultyRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveFacultyById() {
        generateTestData();
        Faculty faculty = facultyRepository.findById(1).get();
        facultyRepository.delete(faculty);
        int expected = 4;
        int actual = facultyRepository.findAll().size();
        assertEquals(expected, actual);
    }

    private void generateTestData() {
        for (int i = 0; i < 5; i++) {
            Faculty faculty = new Faculty();
            faculty.setName(TEST);
            facultyRepository.save(faculty);
        }
    }
}