package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.*;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.exceptions.RepositoryException;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
@ActiveProfiles("test")
class JpaItemRepositoryTest {
    private ItemRepository itemRepository;
    private ScheduleRepository scheduleRepository;
    private SubjectRepository subjectRepository;
    private AudienceRepository audienceRepository;
    private TeacherRepository teacherRepository;
    @Mock
    private JpaItemRepository mockItemRepository;
    private static final String TEST = "Test";

    @Autowired
    public JpaItemRepositoryTest(ItemRepository itemRepository, ScheduleRepository scheduleRepository,
                                 SubjectRepository subjectRepository, AudienceRepository audienceRepository, TeacherRepository teacherRepository) {
        this.itemRepository = itemRepository;
        this.scheduleRepository = scheduleRepository;
        this.subjectRepository = subjectRepository;
        this.audienceRepository = audienceRepository;
        this.teacherRepository = teacherRepository;
    }

    @Test
    void shouldAddItem() throws RepositoryException {
        generateTestData();
        Item expected = new Item(1, new Subject(1, null, null, null), TEST, 1, new Audience(1, 0), 1, new Schedule(1, null, null));
        Item actual = itemRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindItemById() throws NotImplementedException, RepositoryException {
        generateTestData();
        Item expected = new Item(5, new Subject(1, null, null, null), TEST, 1, new Audience(1, 0), 1, new Schedule(1, null, null));
        Item actual = itemRepository.findById(5);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllItems() throws RepositoryException{
        generateTestData();
        int expected = 5;
        int actual = itemRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveItemsById() throws RepositoryException {
        generateTestData();
        itemRepository.removeById(5);
        int expected = 4;
        int actual = itemRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowRepositoryExceptionWhenAddItemMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockItemRepository).add(new Item());
        assertThrows(RepositoryException.class, () -> {
            mockItemRepository.add(new Item());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindByIdItemMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockItemRepository).findById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockItemRepository.findById(0);
        });
    }

    @Test
    void shouldThrowDaoExceptionWhenFindAllItemMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockItemRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockItemRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveByIdMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockItemRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockItemRepository.removeById(anyInt());
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindByIdMethodCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockItemRepository).removeById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockItemRepository.removeById(anyInt());
        });
    }

    private void generateTestData() throws RepositoryException {
        Schedule schedule = new Schedule(TEST);
        scheduleRepository.add(schedule);
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(0).degree(TEST).build();
        Audience audience = new Audience();
        teacherRepository.add(teacher);
        Subject subject = new Subject(TEST, teacherRepository.findById(1), TEST);
        subjectRepository.add(subject);
        audienceRepository.add(audience);
        for (int i = 0; i < 5; i++) {
            itemRepository.add(new Item(subjectRepository.findById(1), TEST, 1, audienceRepository.findById(1), 1, scheduleRepository.findById(1)));
        }
    }
}