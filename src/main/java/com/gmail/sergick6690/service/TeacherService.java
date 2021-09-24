package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.TeacherRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
@Transactional(rollbackFor = ServiceException.class)
public class TeacherService {
    private TeacherRepository teacherRepository;
    private ScheduleService scheduleService;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, ScheduleService scheduleService) {
        this.teacherRepository = teacherRepository;
        this.scheduleService = scheduleService;
    }

    public void add(Teacher teacher) throws ServiceException {
        if (teacher == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            teacherRepository.save(teacher);
            DEBUG.debug((format("New teacher - %s was added", teacher.toString())));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't add teacher - " + teacher + e, e);
        }
    }

    public Teacher findById(int id) throws ServiceException {
        try {
            Teacher teacher = teacherRepository.findById(id).get();
            DEBUG.debug(format("Subject with id - %d was returned", id));
            return teacher;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Teacher not found - " + id + e, e);
        }
    }

    public List<Teacher> findAll() throws ServiceException {
        try {
            List<Teacher> teacherList = teacherRepository.findAll();
            DEBUG.debug("All teachers was returned");
            return teacherList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any teacher " + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            teacherRepository.delete(this.findById(id));
            DEBUG.debug(format("Subject with id - %d is removed", id));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove teacher with id - " + id + e, e);
        }
    }

    public Long findTeachersCountWithEqualDegree(String degree) throws ServiceException {
        try {
            Long count = teacherRepository.findTeachersCountWithEqualDegree(degree);
            DEBUG.debug("Was returned teachers count - " + count + " for teachers with degree - " + degree);
            return count;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any teacher with degree - " + degree + e, e);
        }
    }

    public void removeSchedule(int teacherId) throws ServiceException {
        try {
            Schedule schedule = scheduleService.findById(1);
            Teacher teacher = this.findById(teacherId);
            teacher.setSchedule(schedule);
            DEBUG.debug("Was removed schedule for teacher with id - " + teacherId);
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove schedule for teacher with id - " + teacherId + e, e);
        }
    }

    public void assignSchedule(int teacherId, int scheduleId) throws ServiceException {
        try {
            Schedule schedule = scheduleService.findById(scheduleId);
            Teacher teacher = this.findById(teacherId);
            teacher.setSchedule(schedule);
            DEBUG.debug("Was assigned schedule with id - " + scheduleId + "for teacher with id - " + teacherId);
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't assign schedule with id - " + scheduleId + "for teacher with id - " + teacherId + e, e);
        }
    }

    public void changeSchedule(int teacherId, int scheduleId) throws ServiceException {
        try {
            this.removeSchedule(teacherId);
            this.assignSchedule(teacherId, scheduleId);
            DEBUG.debug("Was changed schedule for teacher with id - " + teacherId + " on schedule with id - " + scheduleId);
        } catch (NoSuchElementException e) {
            ERROR.error("Can't change schedule for teacher with id - " + teacherId, e);
            throw new ServiceException("Can't change schedule for teacher with id - " + teacherId + e, e);
        }
    }
}
