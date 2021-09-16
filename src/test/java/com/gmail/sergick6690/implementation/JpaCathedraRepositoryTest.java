package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.CathedraRepository;
import com.gmail.sergick6690.Repository.FacultyRepository;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Faculty;
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
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JpaCathedraRepositoryTest {
    @Autowired
    private CathedraRepository cathedraRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Mock
    private JpaCathedraRepository mockCathedraRepository;
    private final String TEST = "test";

    @Test
    void shouldAddCathedra() throws RepositoryException {
        generateTestData();
        Cathedra expected = new Cathedra(1, TEST);
        Cathedra actual = cathedraRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldRremoveCathedraById() throws RepositoryException {
        generateTestData();
        cathedraRepository.removeById(1);
        int expected = 4;
        int actual = cathedraRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindCathedraById() throws NotImplementedException, RepositoryException {
        generateTestData();
        Cathedra expected = new Cathedra(4, TEST);
        Cathedra actual = cathedraRepository.findById(4);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void shouldFindAllCathedras() throws RepositoryException {
        generateTestData();
        int expected = 5;
        int actual = cathedraRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddCathedraMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockCathedraRepository).add(new Cathedra(1, TEST, new Faculty(), null));
        assertThrows(RepositoryException.class, () -> {
            mockCathedraRepository.add(new Cathedra(1, TEST, new Faculty(), null));
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindByIdMethodCall() {
        assertThrows(RepositoryException.class, () -> {
            cathedraRepository.findById(0);
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockCathedraRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockCathedraRepository.findById(0);
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockCathedraRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockCathedraRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockCathedraRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockCathedraRepository.removeById(0);
        });
    }

    private void generateTestData() throws RepositoryException {
        facultyRepository.add(new Faculty());
        Faculty faculty = facultyRepository.findById(1);
        for (int i = 0; i < 5; i++) {
            cathedraRepository.add(new Cathedra(TEST, faculty));
        }
    }
}