package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Faculty;
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
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CathedraRepositoryTest {
    @Autowired
    private CathedraRepository cathedraRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    private final String TEST = "test";

    @Test
    void shouldAddCathedra() {
        generateTestData();
        Cathedra expected = new Cathedra(1, TEST);
        Cathedra actual = cathedraRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRremoveCathedraById() {
        generateTestData();
        Cathedra cathedra = cathedraRepository.findById(1).get();
        cathedraRepository.delete(cathedra);
        int expected = 4;
        int actual = cathedraRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindCathedraById() throws NotImplementedException {
        generateTestData();
        Cathedra expected = new Cathedra(4, TEST);
        Cathedra actual = cathedraRepository.findById(4).get();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void shouldFindAllCathedras() {
        generateTestData();
        int expected = 5;
        int actual = cathedraRepository.findAll().size();
        assertEquals(expected, actual);
    }

    private void generateTestData() {
        Faculty facultyTest = new Faculty();
        facultyTest.setName(TEST);
        facultyRepository.save(facultyTest);
        Faculty faculty = facultyRepository.findById(1).get();
        for (int i = 0; i < 5; i++) {
            cathedraRepository.save(new Cathedra(TEST, faculty));
        }
    }
}