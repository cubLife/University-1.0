package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.FacultyRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class FacultyService {
    private FacultyRepository facultyRepository;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public void add(Faculty faculty) throws ServiceException {
        if (faculty == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            facultyRepository.save(faculty);
            DEBUG.debug((format("New faculty - %s was added", faculty.toString())));
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't add faculty - " + faculty + e, e);
        }
    }

    public Faculty findById(int id) throws ServiceException {
        try {
            Faculty faculty = facultyRepository.findById(id).get();
            DEBUG.debug(format("Faculty with id - %d was returned", id));
            return faculty;
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Faculty not found - " + id, e);
        }
    }

    public List<Faculty> findAll() throws ServiceException {
        try {
            List<Faculty> facultyList = facultyRepository.findAll();
            DEBUG.debug("All faculties was returned");
            return facultyList;
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any faculties", e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            facultyRepository.delete(this.findById(id));
            DEBUG.debug(format("Cathedra with id - %d is removed", id));
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove faculty with id - " + id, e);
        }
    }
}