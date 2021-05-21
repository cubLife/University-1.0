package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.GenericDao;
import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcSubjectDAO;
import com.gmail.sergick6690.university.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class SubjectService implements GenericDao<Subject> {
    private SubjectDAO subjectDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public SubjectService(JdbcSubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Override
    public void add(Subject subject) throws ServiceException {
        if (subject == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            subjectDAO.add(subject);
            DEBUG.debug((format("New subject - %s was added", subject.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Subject findById(int id) throws ServiceException {
        try {
            Subject subject = subjectDAO.findById(id);
            DEBUG.debug(format("Subject with id - %d was returned", id));
            return subject;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Subject> findAll() throws ServiceException {
        try {
            List<Subject> subjectList = subjectDAO.findAll();
            DEBUG.debug("All subjects was returned");
            return subjectList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            subjectDAO.removeById(id);
            DEBUG.debug(format("Subject with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public List<Subject> findAllSubjectRelatedToAudience(int id) throws ServiceException {
        try {
            List<Subject> subjectList = subjectDAO.findAllSubjectRelatedToAudience(id);
            DEBUG.debug("All subjects related to audience with id - " + id + " was returned");
            return subjectList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void assignTeacher(int subjectId, int teacherId) throws ServiceException {
        try {
            subjectDAO.assignTeacher(subjectId, teacherId);
            DEBUG.debug("For subject with id" + subjectId + " was assigned teacher with id - " + teacherId);
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void removeTeacher(int subjectId) throws ServiceException {
        try {
            subjectDAO.removeTeacher(subjectId);
            DEBUG.debug("For subject with id - " + subjectId + " was removed teacher");
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void changeTeacher(int subjectId, int teacherId) throws ServiceException {
        try {
            subjectDAO.removeTeacher(subjectId);
            subjectDAO.assignTeacher(subjectId, teacherId);
            DEBUG.debug("For subject with id -" + subjectId + " was changed teacher on teacher with id - " + teacherId);
        } catch (DaoException e) {
            ERROR.error("Can't change teacher for subject with id - " + subjectId, e);
            throw new ServiceException("Can't change teacher for subject with id - " + subjectId, e);
        }
    }
}
