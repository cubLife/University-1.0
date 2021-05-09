package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Subject;

import java.util.List;

public interface SubjectDAO extends CrudMethods<Subject> {
    @Override
    default void add(Subject obj) throws DaoException {
    }

    @Override
    default Subject findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Subject> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }

    public List<Subject> findAllSubjectRelatedToAudience(int id) throws DaoException;

    public void assignTeacher(int subjectId, int teacherId) throws DaoException;

    public void removeTeacher(int subjectId) throws DaoException;
}
