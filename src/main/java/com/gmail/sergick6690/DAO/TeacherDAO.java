package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Teacher;

import java.util.List;

public interface TeacherDAO extends GenericDao<Teacher> {

    @Override
    default void add(Teacher obj) throws DaoException {
    }

    @Override
    default Teacher findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Teacher> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }

    public Integer findTeachersCountWithEqualDegree(String degree) throws DaoException;

    public void removeSchedule(int teacherId) throws DaoException;

    public void assignSchedule(int teacherId, int scheduleId) throws DaoException;
}
