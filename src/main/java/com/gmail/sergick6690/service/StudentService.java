package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.StudentRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.StudentForm;
import com.gmail.sergick6690.universityModels.Group;
import com.gmail.sergick6690.universityModels.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    private GroupService groupService;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");
    private static final int DEFAULT_COURSE = 1;

    @Autowired
    public StudentService(StudentRepository studentRepository, GroupService groupService) {
        this.studentRepository = studentRepository;
        this.groupService = groupService;
    }

    public void add(Student student) throws ServiceException {
        if (student == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            studentRepository.save(student);
            DEBUG.debug((format("New student - %s was added", student.toString())));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't add student - " + student + e, e);
        }
    }

    public Student findById(int id) throws ServiceException {
        try {
            Student student = studentRepository.findById(id).get();
            DEBUG.debug(format("Student with id - %d was returned", id));
            return student;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Student not found - " + id + e, e);
        }
    }

    public List<Student> findAll() throws ServiceException {
        try {
            List<Student> studentList = studentRepository.findAll();
            DEBUG.debug("All students was returned");
            return studentList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any student " + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            studentRepository.delete(this.findById(id));
            DEBUG.debug(format("Student with id - %d is removed", id));
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove student with id - " + id + e, e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void assignGroup(int studentId, int groupId) throws ServiceException {
        try {
            Group group = groupService.findById(groupId);
            Student student = this.findById(studentId);
            student.setGroup(group);
            DEBUG.debug("Group with id - " + groupId + " was assigned to student with id - " + studentId);
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void removeFromGroup(int studentId) throws ServiceException {
        try {
            Group group = groupService.findById(1);
            Student student = this.findById(studentId);
            student.setGroup(group);
            DEBUG.debug("Student with id - " + studentId + " was removed from group");
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }

    }

    @Transactional(rollbackFor = ServiceException.class)
    public void assignCourse(int studentId, int course) throws ServiceException {
        try {
            Student student = this.findById(studentId);
            student.setCourse(course);
            DEBUG.debug("Course number - " + course + " was assigned to student with id - " + studentId);
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't assign course - " + course + "for student - " + studentId + e, e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void removeFromCourse(int studentId) throws ServiceException {
        try {
            Student student = this.findById(studentId);
            student.setCourse(DEFAULT_COURSE);
            DEBUG.debug("For student with id - " + studentId + " was removed course");
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove student with id - " + studentId + " from course " + e, e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void changeGroup(int studentId, int groupId) throws ServiceException {
        try {
            this.removeFromGroup(studentId);
            this.assignGroup(studentId, groupId);
            DEBUG.debug("For student with id - " + studentId + " was changed group on group with id - " + groupId);
        } catch (NoSuchElementException e) {
            ERROR.error("Can't change group for student with id - " + studentId, e);
            throw new ServiceException("Can't change group for student with id - " + studentId + e, e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void changeCourse(int studentId, int course) throws ServiceException {
        try {
            this.removeFromCourse(studentId);
            this.assignCourse(studentId, course);
            DEBUG.debug("For student with id - " + studentId + " was changed course on course with id - " + course);
        } catch (NoSuchElementException e) {
            ERROR.error("Can't change course for student with id - " + studentId, e);
            throw new ServiceException("Can't change course for student with id - " + studentId + e, e);
        }
    }

    public Student createNewStudent(StudentForm studentForm) throws ServiceException {
        Group group = groupService.findById(studentForm.getGroupId());
        return Student.builder().firstName(studentForm.getFirstName()).lastNAme(studentForm.getLastName())
                .sex(studentForm.getSex()).age(studentForm.getAge()).course(studentForm.getCourse()).group(group).build();
    }
}