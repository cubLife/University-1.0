package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.*;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
@ActiveProfiles("test")
class ItemRepositoryTest {
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
    private static final String TEST = "Test";

    @Test
    void shouldAddItem() {
        generateTestData();
        Item expected = new Item(1, new Subject(1, TEST, teacherRepository.findById(1).get(), TEST+TEST+TEST), TEST, 9, new Audience(1, 0), 1, new Schedule(1, TEST, null));
        Item actual = itemRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindItemById() throws NotImplementedException {
        generateTestData();
        Item expected = new Item(5, new Subject(1, TEST, teacherRepository.findById(1).get(), TEST+TEST+TEST), TEST, 9, new Audience(1, 0), 1, new Schedule(1, TEST, null));
        Item actual = itemRepository.findById(5).get();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllItems() {
        generateTestData();
        int expected = 5;
        int actual = itemRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveItemsById() {
        generateTestData();
        Item item = itemRepository.getById(1);
        itemRepository.delete(item);
        int expected = 4;
        int actual = itemRepository.findAll().size();
        assertEquals(expected, actual);
    }

    private void generateTestData() {
        Schedule schedule = new Schedule(TEST+1);
        scheduleRepository.save(schedule);
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST).build();
        teacherRepository.save(teacher);
        Audience audience = new Audience();
        Subject subject = new Subject(TEST, teacherRepository.findById(1).get(), TEST+TEST+TEST);
        subjectRepository.save(subject);
        audienceRepository.save(audience);
        for (int i = 0; i < 5; i++) {
            itemRepository.save(new Item(subjectRepository.findById(1).get(), TEST, 9, audienceRepository.findById(1).get(), 1, scheduleRepository.findById(1).get()));
        }
    }
}