package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.GenericDao;
import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcStudentDAO;
import com.gmail.sergick6690.university.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@Transactional
public class StudentService implements GenericDao<Student> {
    private StudentDAO studentDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public StudentService(JdbcStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public void add(Student student) throws ServiceException {
        if (student == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            studentDAO.add(student);
            DEBUG.debug((format("New student - %s was added", student.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Student findById(int id) throws ServiceException {
        try {
            Student student = studentDAO.findById(id);
            DEBUG.debug(format("Student with id - %d was returned", id));
            return student;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Student> findAll() throws ServiceException {
        try {
            List<Student> studentList = studentDAO.findAll();
            DEBUG.debug("All students was returned");
            return studentList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            studentDAO.removeById(id);
            DEBUG.debug(format("Student with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void assignGroup(int studentId, int groupId) throws ServiceException {
        try {
            studentDAO.assignGroup(groupId, studentId);
            DEBUG.debug("Group with id - " + groupId + " was assigned to student with id - " + studentId);
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void removeFromGroup(int studentId) throws ServiceException {
        try {
            studentDAO.removeFromGroup(studentId);
            DEBUG.debug("Student with id - " + studentId + " was removed from group");
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }

    }

    public void assignCourse(int studentId, int course) throws ServiceException {
        try {
            studentDAO.assignCourse(studentId, course);
            DEBUG.debug("Course number - " + course + " was assigned to student with id - " + studentId);
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void removeFromCourse(int studentId) throws ServiceException {
        try {
            studentDAO.removeFromCourse(studentId);
            DEBUG.debug("For student with id - " + studentId + " was removed course");
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void changeGroup(int studentId, int groupId) throws ServiceException {
        try {
            studentDAO.removeFromGroup(studentId);
            studentDAO.assignGroup(studentId, groupId);
            DEBUG.debug("For student with id - " + studentId + " was changed group on group with id - " + groupId);
        } catch (DaoException e) {
            ERROR.error("Can't change group for student with id - " + studentId, e);
            throw new ServiceException("Can't change group for student with id - " + studentId, e);
        }
    }

    public void changeCourse(int studentId, int course) throws ServiceException {
        try {
            studentDAO.removeFromCourse(studentId);
            studentDAO.assignCourse(studentId, course);
            DEBUG.debug("For student with id - " + studentId + " was changed course on course with id - " + course);
        } catch (DaoException e) {
            ERROR.error("Can't change course for student with id - " + studentId, e);
            throw new ServiceException("Can't change course for student with id - " + studentId, e);
        }
    }
}