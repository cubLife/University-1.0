package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.universityModels.Cathedra;
import com.gmail.sergick6690.universityModels.Faculty;
import com.gmail.sergick6690.universityModels.Group;
import com.gmail.sergick6690.universityModels.Schedule;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CathedraRepository cathedraRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    private static final String TEST = "test";

    @Test
    void shouldAddGroup() {
        generateTestData();
        Group expected = new Group(1, TEST + 1, null, new Schedule(1, TEST + TEST, null), new Cathedra(1, TEST, new Faculty(1, TEST, null), null));
        Group actual = groupRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindGroupById() throws NotImplementedException {
        generateTestData();
        Group expected = new Group(5, TEST + 1, null, new Schedule(1, TEST + TEST, null), new Cathedra(2, TEST, new Faculty(1, TEST, null), null));
        Group actual = groupRepository.findById(5).get();
        assertEquals(expected, actual);
    }

    @Test
    void findAllGroups() {
        generateTestData();
        int expected = 10;
        int actual = groupRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeGroupById() {
        generateTestData();
        Group group = groupRepository.findById(1).get();
        groupRepository.delete(group);
        int expected = 9;
        int actual = groupRepository.findAll().size();
        assertEquals(expected, actual);
    }

    public void generateTestData() {
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyRepository.save(faculty);
        cathedraRepository.save(new Cathedra(TEST, faculty));
        cathedraRepository.save(new Cathedra(TEST, faculty));
        scheduleRepository.save(new Schedule(TEST + TEST));
        for (int i = 0; i < 5; i++) {
            groupRepository.save(new Group(TEST + 1, scheduleRepository.findById(1).get(), cathedraRepository.findById(1).get()));
            groupRepository.save(new Group(TEST + 1, scheduleRepository.findById(1).get(), cathedraRepository.findById(2).get()));
        }
    }
}