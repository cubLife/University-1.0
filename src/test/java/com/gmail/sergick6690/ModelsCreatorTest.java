package com.gmail.sergick6690;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.*;
import com.gmail.sergick6690.universityModels.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ModelsCreatorTest {
    @MockBean
    private ModelsCreator modelsCreator;
    private static final String TEST = "Test";
    private static final int VALUE = 1;

    @Test
    void shouldCreateNewCathedra() throws ServiceException {
        CathedraForm cathedraForm = new CathedraForm(TEST, 1);
        when(modelsCreator.createNewCathedra(cathedraForm)).thenReturn(new Cathedra(VALUE, TEST, new Faculty(VALUE, TEST), null));
        Cathedra expected = new Cathedra(VALUE, TEST, new Faculty(VALUE, TEST), null);
        Cathedra actual = modelsCreator.createNewCathedra(cathedraForm);
        assertEquals(expected, actual);

    }

    @Test
    void shouldCreateNewTeacher() throws ServiceException {
        TeacherForm teacherForm = new TeacherForm(TEST, TEST, TEST, VALUE, TEST, VALUE);
        Teacher teacher = Teacher.builder().id(VALUE).firstName(TEST).lastName(TEST).sex(TEST).age(VALUE).degree(TEST).schedule(new Schedule(TEST)).build();
        when(modelsCreator.createNewTeacher(teacherForm)).thenReturn(teacher);
        Teacher expected = Teacher.builder().id(VALUE).firstName(TEST).lastName(TEST).sex(TEST).age(VALUE).degree(TEST).schedule(new Schedule(TEST)).build();
        Teacher actual = modelsCreator.createNewTeacher(teacherForm);
        assertEquals(expected, actual);
    }

    @Test
    void shouldCreateNewSubject() throws ServiceException {
        SubjectForm subjectForm = new SubjectForm(TEST, VALUE, TEST);
        when(modelsCreator.createNewSubject(subjectForm)).thenReturn(new Subject(1, TEST, new Teacher(), TEST));
        Subject expected = new Subject(1, TEST, new Teacher(), TEST);
        Subject actual = modelsCreator.createNewSubject(subjectForm);
        assertEquals(expected, actual);

    }

    @Test
    void shouldCreateNewItem() throws ServiceException {
        ItemForm itemForm = new ItemForm(VALUE, "Monday", VALUE, VALUE, VALUE, VALUE);
        when(modelsCreator.createNewItem(itemForm)).thenReturn(new Item(VALUE, new Subject(), "monday", 17, new Audience(), 1, new Schedule()));
        Item expected = new Item(VALUE, new Subject(), "monday", 17, new Audience(), 1, new Schedule());
        Item actual = modelsCreator.createNewItem(itemForm);
        assertEquals(expected, actual);
    }

    @Test
    void shouldCreateNewGroup() throws ServiceException {
        GroupForm groupForm = new GroupForm(TEST, VALUE, VALUE);
        when(modelsCreator.createNewGroup(groupForm)).thenReturn(new Group(VALUE, TEST, null, new Schedule(), new Cathedra()));
        Group expected = new Group(VALUE, TEST, null, new Schedule(), new Cathedra());
        Group actual = modelsCreator.createNewGroup(groupForm);
        assertEquals(expected, actual);
    }

    @Test
    void shouldCreateNewStudent() throws ServiceException {
        StudentForm studentForm = new StudentForm(TEST, TEST, TEST, VALUE, VALUE, VALUE);
        when(modelsCreator.createNewStudent(studentForm)).thenReturn(Student.builder().id(VALUE).firstName(TEST).lastNAme(TEST).sex(TEST).age(VALUE).course(VALUE).group(new Group()).build());
        Student expected = Student.builder().id(VALUE).firstName(TEST).lastNAme(TEST).sex(TEST).age(VALUE).course(VALUE).group(new Group()).build();
        Student actual = modelsCreator.createNewStudent(studentForm);
        assertEquals(expected, actual);
    }
}