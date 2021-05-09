package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcTeacherDAO;
import com.gmail.sergick6690.university.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class TeacherService implements CrudMethods<Teacher> {
    private TeacherDAO teacherDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public TeacherService(JdbcTeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public void add(Teacher teacher) throws ServiceException {
        if (teacher == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            teacherDAO.add(teacher);
            DEBUG.debug((format("New teacher - %s was added", teacher.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Teacher findById(int id) throws ServiceException {
        try {
            Teacher teacher = teacherDAO.findById(id);
            DEBUG.debug(format("Subject with id - %d was returned", id));
            return teacher;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Teacher> findAll() throws ServiceException {
        try {
            List<Teacher> teacherList = teacherDAO.findAll();
            DEBUG.debug("All teachers was returned");
            return teacherList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            teacherDAO.removeById(id);
            DEBUG.debug(format("Subject with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public Integer findTeachersCountWithEqualDegree(String degree) throws ServiceException {
        try {
            int count = teacherDAO.findTeachersCountWithEqualDegree(degree);
            DEBUG.debug("Was returned teachers count - " + count + " for teachers with degree - " + degree);
            return count;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void removeSchedule(int teacherId) throws ServiceException {
        try {
            teacherDAO.removeSchedule(teacherId);
            DEBUG.debug("Was removed schedule for teacher with id - " + teacherId);
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void assignSchedule(int teacherId, int scheduleId) throws ServiceException {
        try {
            teacherDAO.assignSchedule(teacherId, scheduleId);
            DEBUG.debug("Was assigned schedule with id - " + scheduleId + "for teacher with id - " + teacherId);
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void changeSchedule(int teacherId, int scheduleId) throws ServiceException {
        try {
            teacherDAO.removeSchedule(teacherId);
            teacherDAO.assignSchedule(teacherId, scheduleId);
            DEBUG.debug("Was changed schedule for teacher with id - " + teacherId + " on schedule with id - " + scheduleId);
        } catch (DaoException e) {
            ERROR.error("Can't change schedule for teacher with id - " + teacherId, e);
            throw new ServiceException("Can't change schedule for teacher with id - " + teacherId, e);
        }
    }
}
