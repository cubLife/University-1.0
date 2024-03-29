package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.*;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class SubjectRepositoryTest {
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
    private static final String TEST = "Test1";

    @Test
    void shouldAddSubject() {
        generateTestData();
        Subject expected = new Subject(1, TEST, teacherRepository.findById(1).get(), TEST + TEST);
        Subject actual = subjectRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void findSubjectById() throws NotImplementedException {
        generateTestData();
        Subject expected = new Subject(4, TEST, teacherRepository.findById(1).get(), TEST + TEST);
        Subject actual = subjectRepository.findById(4).get();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjects() {
        generateTestData();
        int expected = 5;
        int actual = subjectRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeSubjectById() {
        generateTestData();
        Subject subject = subjectRepository.findById(2).get();
        subjectRepository.delete(subject);
        int expected = 4;
        System.out.println(subjectRepository.findAll());
        int actual = subjectRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void findAllSubjectRelatedToAudience() {
        generateTestData();
        int expected = 5;
        int actual = subjectRepository.findAllSubjectRelatedToAudience(2).size();
        assertEquals(expected, actual);
    }

    private void generateTestData() {
        scheduleRepository.save(new Schedule(TEST));
        Teacher teacher = Teacher.builder().firstName(TEST).lastName(TEST).sex(TEST).age(1).degree(TEST).build();
        teacherRepository.save(teacher);
        Audience audience = new Audience();
        audience.setNumber(0);
        Audience audience1 = new Audience();
        audience1.setNumber(1);
        audienceRepository.save(audience);
        audienceRepository.save(audience1);
        for (int i = 0; i < 5; i++) {
            subjectRepository.save(new Subject(TEST, teacherRepository.findById(1).get(), TEST + TEST));
            itemRepository.save(new Item(subjectRepository.findById(1).get(), TEST, 9, audienceRepository.findById(1).get(), 1, scheduleRepository.findById(1).get()));
            itemRepository.save(new Item(subjectRepository.findById(1).get(), TEST, 9, audienceRepository.findById(2).get(), 1, scheduleRepository.findById(1).get()));
        }
    }
}