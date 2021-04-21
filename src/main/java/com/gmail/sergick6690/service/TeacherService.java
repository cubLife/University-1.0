package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.implementation.JdbcTeacherDAO;
import com.gmail.sergick6690.university.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements CrudMethods<Teacher> {
    private TeacherDAO teacherDAO;

    @Autowired
    public TeacherService(JdbcTeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public void add(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        teacherDAO.add(teacher);
    }

    @Override
    public Teacher findById(int id) {
        return teacherDAO.findById(id);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        teacherDAO.removeById(id);
    }

    public Integer findTeachersCountWithEqualDegree(String degree) {
        return teacherDAO.findTeachersCountWithEqualDegree(degree);
    }

    public void removeSchedule(int teacherId) {
        teacherDAO.removeSchedule(teacherId);
    }

    public void assignSchedule(int teacherId, int scheduleId) {
        teacherDAO.assignSchedule(teacherId, scheduleId);
    }

    public void changeSchedule(int teacherId, int scheduleId) {
        teacherDAO.removeSchedule(teacherId);
        teacherDAO.assignSchedule(teacherId, scheduleId);
    }
}
