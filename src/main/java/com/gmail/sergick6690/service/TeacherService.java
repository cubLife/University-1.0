package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@Transactional(rollbackFor = ServiceException.class)
public class TeacherService {
    private TeacherDAO teacherDAO;
    private ScheduleDAO scheduleDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public TeacherService(TeacherDAO teacherDAO, ScheduleDAO scheduleDAO) {
        this.teacherDAO = teacherDAO;
        this.scheduleDAO = scheduleDAO;
    }

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

    public void removeById(int id) throws ServiceException {
        try {
            teacherDAO.removeById(id);
            DEBUG.debug(format("Subject with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public Long findTeachersCountWithEqualDegree(String degree) throws ServiceException {
        try {
            Long count = teacherDAO.findTeachersCountWithEqualDegree(degree);
            DEBUG.debug("Was returned teachers count - " + count + " for teachers with degree - " + degree);
            return count;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void removeSchedule(int teacherId) throws ServiceException {
        try {
            Schedule schedule = scheduleDAO.findById(1);
            Teacher teacher = teacherDAO.findById(teacherId);
            teacher.setSchedule(schedule);
            DEBUG.debug("Was removed schedule for teacher with id - " + teacherId);
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void assignSchedule(int teacherId, int scheduleId) throws ServiceException {
        try {
            Schedule schedule = scheduleDAO.findById(scheduleId);
            Teacher teacher = teacherDAO.findById(teacherId);
            teacher.setSchedule(schedule);
            DEBUG.debug("Was assigned schedule with id - " + scheduleId + "for teacher with id - " + teacherId);
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void changeSchedule(int teacherId, int scheduleId) throws ServiceException {
        try {
            this.removeSchedule(teacherId);
            this.assignSchedule(teacherId, scheduleId);
            DEBUG.debug("Was changed schedule for teacher with id - " + teacherId + " on schedule with id - " + scheduleId);
        } catch (Exception e) {
            ERROR.error("Can't change schedule for teacher with id - " + teacherId, e);
            throw new ServiceException("Can't change schedule for teacher with id - " + teacherId, e);
        }
    }
}
