package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Student;

import java.util.List;

public interface StudentDAO extends GenericDao<Student> {
    @Override
    default void add(Student obj) throws DaoException {
    }

    @Override
    default Student findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Student> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }

    public void assignCourse(int studentId, int course) throws DaoException;

    public void removeFromCourse(int studentId) throws DaoException;
}