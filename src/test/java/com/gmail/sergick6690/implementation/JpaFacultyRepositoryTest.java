package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.FacultyRepository;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.exceptions.RepositoryException;
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
class JpaFacultyRepositoryTest {
    private FacultyRepository facultyRepository;
    @Mock
    private JpaFacultyRepository mockFacultyRepository;
    private static final String TEST = "test";

    @Autowired
    public JpaFacultyRepositoryTest(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Test
    void shouldAddFaculty() throws RepositoryException {
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyRepository.add(faculty);
        Faculty expected = new Faculty(1, "test");
        Faculty actual = facultyRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindFacultyById() throws NotImplementedException, RepositoryException {
        for (int i = 0; i < 5; i++) {
            Faculty faculty = new Faculty();
            faculty.setName(TEST);
            facultyRepository.add(faculty);
        }
        Faculty expected = (new Faculty(4, TEST));
        Faculty actual = facultyRepository.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllFaculties() throws RepositoryException {
        for (int i = 0; i < 5; i++) {
            facultyRepository.add(new Faculty());
        }
        int expected = 5;
        int actual = facultyRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveFacultyById() throws RepositoryException {
        for (int i = 0; i < 5; i++) {
            facultyRepository.add(new Faculty());
        }
        facultyRepository.removeById(1);
        int expected = 4;
        int actual = facultyRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddFacultyMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockFacultyRepository).add(new Faculty());
        assertThrows(RepositoryException.class, () -> {
            mockFacultyRepository.add(new Faculty());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockFacultyRepository).findById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockFacultyRepository.findById(0);
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockFacultyRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockFacultyRepository.findById(0);
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockFacultyRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockFacultyRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveByIdFacultyMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockFacultyRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockFacultyRepository.removeById(0);
        });
    }
}