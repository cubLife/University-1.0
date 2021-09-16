package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.CathedraRepository;
import com.gmail.sergick6690.Repository.FacultyRepository;
import com.gmail.sergick6690.Repository.GroupRepository;
import com.gmail.sergick6690.Repository.ScheduleRepository;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Faculty;
import com.gmail.sergick6690.university.Group;
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
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JpaGroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CathedraRepository cathedraRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Mock
    private JpaGroupRepository mockGroupRepository;
    private static final String TEST = "test";

    @Test
    void shouldAddGroup() throws RepositoryException {
        generateTestData();
        Group expected = new Group(1, TEST, null, new Schedule(1, null, null), new Cathedra(1, TEST, new Faculty(1, TEST, null), null));
        Group actual = groupRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindGroupById() throws NotImplementedException, RepositoryException {
        generateTestData();
        Group expected = new Group(5, TEST, null, new Schedule(1, null, null), new Cathedra(2, TEST, new Faculty(1, TEST, null), null));
        Group actual = groupRepository.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroups() throws RepositoryException {
        generateTestData();
        int expected = 10;
        int actual = groupRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeGroupById() throws RepositoryException {
        generateTestData();
        groupRepository.removeById(1);
        int expected = 9;
        int actual = groupRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddFGroupMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockGroupRepository).add(new Group());
        assertThrows(RepositoryException.class, () -> {
            mockGroupRepository.add(new Group());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindGroupByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockGroupRepository).findById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockGroupRepository.findById(0);
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindGroupByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockGroupRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockGroupRepository.findById(0);
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFidAllFGroupMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockGroupRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockGroupRepository.findAll();
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenRemoveFGroupByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockGroupRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockGroupRepository.removeById(0);
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllGroupsRelatedToCathedraMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockGroupRepository).findAllGroupsRelatedToCathedra(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockGroupRepository.findAllGroupsRelatedToCathedra(anyInt());
        });
    }

    public void generateTestData() throws RepositoryException {
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyRepository.add(faculty);
        cathedraRepository.add(new Cathedra(TEST, faculty));
        cathedraRepository.add(new Cathedra(TEST, faculty));
        scheduleRepository.add(new Schedule());
        for (int i = 0; i < 5; i++) {
            groupRepository.add(new Group(TEST, scheduleRepository.findById(1), cathedraRepository.findById(1)));
            groupRepository.add(new Group(TEST, scheduleRepository.findById(1), cathedraRepository.findById(2)));
        }
    }
}