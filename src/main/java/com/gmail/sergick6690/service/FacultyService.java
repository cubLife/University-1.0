package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.implementation.JdbcFacultyDAO;
import com.gmail.sergick6690.university.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService implements CrudMethods<Faculty> {
    private JdbcFacultyDAO facultyDAO;

    @Autowired
    public FacultyService(JdbcFacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    @Override
    public void add(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        facultyDAO.add(faculty);
    }

    @Override
    public Faculty findById(int id) {
        return facultyDAO.findById(id);
    }

    @Override
    public List<Faculty> findAll() {
        return facultyDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        facultyDAO.removeById(id);
    }
}