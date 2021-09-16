package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Subject;

import java.util.List;

public interface SubjectRepository extends GenericRepository<Subject> {
    @Override
    default void add(Subject obj) throws RepositoryException {
    }

    @Override
    default Subject findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Subject> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }

    public List<Subject> findAllSubjectRelatedToAudience(int id) throws RepositoryException;
}