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
class JpaStudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CathedraRepository cathedraRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Mock
    private JpaStudentRepository mockStudentRepository;
    private static final String TEST = "test";

    @Test
    void shouldAddStudent() throws RepositoryException {
        createTestData();
        studentRepository.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(0).build());
        Student expected = Student.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(0).build();
        Student actual = studentRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindStudentById() throws NotImplementedException, RepositoryException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentRepository.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(0).build());
        }
        Student expected = Student.builder().id(3).firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(0).build();
        Student actual = studentRepository.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllStudents() throws RepositoryException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentRepository.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(0).build());
        }
        int expected = 5;
        int actual = studentRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveStudentById() throws RepositoryException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentRepository.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(0).build());
        }
        studentRepository.removeById(1);
        int expected = 4;
        int actual = studentRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAssignCourse() throws RepositoryException {
        createTestData();
        studentRepository.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(0).build());
        studentRepository.assignCourse(1, 4);
        int expected = 4;
        int actual = studentRepository.findById(1).getCourse();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveFromCourse() throws RepositoryException {
        createTestData();
        studentRepository.add(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(0).group(groupRepository.findById(1)).course(4).build());
        studentRepository.removeFromCourse(1);
        int expected = 0;
        int actual = studentRepository.findById(1).getCourse();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddStudentMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockStudentRepository).add(new Student());
        assertThrows(RepositoryException.class, () -> {
            mockStudentRepository.add(new Student());
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindStudentByIdMethodCall() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> {
            studentRepository.findById(1);
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindStudentByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockStudentRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockStudentRepository.findById(anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllStudentMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockStudentRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockStudentRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveStudentByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockStudentRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockStudentRepository.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAssignCourseMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockStudentRepository).assignCourse(anyInt(), anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockStudentRepository.assignCourse(anyInt(), anyInt());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveFromCourseMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockStudentRepository).removeFromCourse(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockStudentRepository.removeFromCourse(anyInt());
        });
    }

    private void createTestData() throws RepositoryException {
        scheduleRepository.add(new Schedule());
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyRepository.add(faculty);
        cathedraRepository.add(new Cathedra(TEST, facultyRepository.findById(1)));
        groupRepository.add(new Group(TEST, scheduleRepository.findById(1), cathedraRepository.findById(1)));
        groupRepository.add(new Group(TEST, scheduleRepository.findById(1), cathedraRepository.findById(1)));
    }

}