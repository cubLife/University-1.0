package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcFacultyDAO;
import com.gmail.sergick6690.university.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class FacultyService implements CrudMethods<Faculty> {
    private JdbcFacultyDAO facultyDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public FacultyService(JdbcFacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    @Override
    public void add(Faculty faculty) throws ServiceException {
        if (faculty == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            facultyDAO.add(faculty);
            DEBUG.debug((format("New faculty - %s was added", faculty.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Faculty findById(int id) throws ServiceException {
        try {
            Faculty faculty = facultyDAO.findById(id);
            DEBUG.debug(format("Faculty with id - %d was returned", id));
            return faculty;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Faculty> findAll() throws ServiceException {
        try {
            List<Faculty> facultyList = facultyDAO.findAll();
            DEBUG.debug("All faculties was returned");
            return facultyList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            facultyDAO.removeById(id);
            DEBUG.debug(format("Cathedra with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}