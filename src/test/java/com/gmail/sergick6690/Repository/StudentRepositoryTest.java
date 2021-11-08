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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class StudentRepositoryTest {
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
    private static final String TEST = "test1";

    @Test
    void shouldAddStudent() {
        createTestData();
        studentRepository.save(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(1).group(groupRepository.findById(1).get()).course(1).build());
        Student expected = Student.builder().id(1).firstName(TEST).lastNAme(TEST).sex(TEST).age(1).group(groupRepository.findById(1).get()).course(1).build();
        Student actual = studentRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindStudentById() throws NotImplementedException {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentRepository.save(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(1).group(groupRepository.findById(1).get()).course(1).build());
        }
        Student expected = Student.builder().id(3).firstName(TEST).lastNAme(TEST).sex(TEST).age(1).group(groupRepository.findById(1).get()).course(1).build();
        Student actual = studentRepository.findById(3).get();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllStudents() {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentRepository.save(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(1).group(groupRepository.findById(1).get()).course(1).build());
        }
        int expected = 5;
        int actual = studentRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveStudentById() {
        createTestData();
        for (int i = 0; i < 5; i++) {
            studentRepository.save(Student.builder().firstName(TEST).lastNAme(TEST).sex(TEST).age(1).group(groupRepository.findById(1).get()).course(1).build());
        }
        Student student = studentRepository.findById(1).get();
        studentRepository.delete(student);
        int expected = 4;
        int actual = studentRepository.findAll().size();
        assertEquals(expected, actual);
    }


    private void createTestData() {
        scheduleRepository.save(new Schedule(TEST));
        Faculty faculty = new Faculty();
        faculty.setName(TEST);
        facultyRepository.save(faculty);
        cathedraRepository.save(new Cathedra(TEST, facultyRepository.findById(1).get()));
        groupRepository.save(new Group(TEST, scheduleRepository.findById(1).get(), cathedraRepository.findById(1).get()));
        groupRepository.save(new Group(TEST, scheduleRepository.findById(1).get(), cathedraRepository.findById(1).get()));
    }

}