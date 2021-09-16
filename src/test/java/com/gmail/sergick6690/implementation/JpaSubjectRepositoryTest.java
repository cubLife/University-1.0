package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.*;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.*;
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
class JpaSubjectRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private AudienceRepository audienceRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Mock
    private SubjectRepository mockSubjectRepository;
    private static final String TEST = "Test";

    @Test
    void shouldAddSubject() throws RepositoryException {
        generateTestData();
        Subject expected = new Subject(1, TEST, teacherRepository.findById(1), TEST);
        Subject actual = subjectRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void findSubjectById() throws NotImplementedException, RepositoryException {
        generateTestData();
        Subject expected = new Subject(4, TEST, teacherRepository.findById(1), TEST);
        Subject actual = subjectRepository.findById(4);
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjects() throws RepositoryException {
        generateTestData();
        int expected = 5;
        int actual = subjectRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeSubjectById() throws RepositoryException {
        generateTestData();
        subjectRepository.removeById(2);
        int expected = 4;
        int actual = subjectRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjectRelatedToAudience() throws RepositoryException {
        generateTestData();
        int expected = 5;
        int actual = subjectRepository.findAllSubjectRelatedToAudience(2).size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddSubjectMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockSubjectRepository).add(new Subject());
        assertThrows(RepositoryException.class, () -> {
            mockSubjectRepository.add(new Subject());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindByIdMethodCall() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> {
            subjectRepository.findById(1);
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockSubjectRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockSubjectRepository.findById(anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllSubjectsMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockSubjectRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockSubjectRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveSubjectByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockSubjectRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockSubjectRepository.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllSubjectRelatedToAudienceMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockSubjectRepository).findAllSubjectRelatedToAudience(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockSubjectRepository.findAllSubjectRelatedToAudience(anyInt());
        });
    }

    private void generateTestData() throws RepositoryException {
        scheduleRepository.add(new Schedule(TEST));
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).build();
        teacherRepository.add(teacher);
        Audience audience = new Audience();
        audience.setNumber(0);
        Audience audience1 = new Audience();
        audience1.setNumber(1);
        audienceRepository.add(audience);
        audienceRepository.add(audience1);
        for (int i = 0; i < 5; i++) {
            subjectRepository.add(new Subject(TEST, teacherRepository.findById(1), TEST));
            itemRepository.add(new Item(subjectRepository.findById(1), TEST, 1, audienceRepository.findById(1), 1, scheduleRepository.findById(1)));
            itemRepository.add(new Item(subjectRepository.findById(1), TEST, 1, audienceRepository.findById(2), 1, scheduleRepository.findById(1)));
        }
    }
}