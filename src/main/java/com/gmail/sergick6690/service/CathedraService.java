package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.CathedraRepository;
import com.gmail.sergick6690.Repository.FacultyRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.CathedraForm;
import com.gmail.sergick6690.universityModels.Cathedra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class CathedraService {
    private CathedraRepository cathedraRepository;
    private FacultyRepository facultyRepository;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public CathedraService(CathedraRepository cathedraRepository, FacultyRepository facultyRepository) {
        this.cathedraRepository = cathedraRepository;
        this.facultyRepository=facultyRepository;
    }

    public void add(Cathedra cathedra) throws ServiceException {
        if (cathedra == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            cathedraRepository.save(cathedra);
            DEBUG.debug((format("New cathedra - %s was added", cathedra.toString())));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cant't add cathedra" + e, e);
        }
    }

    public Cathedra findById(int id) throws ServiceException {
        try {
            Cathedra cathedra = cathedraRepository.findById(id).get();
            DEBUG.debug(format("Cathedra with id - %d was returned", id));
            return cathedra;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cathedra not found - " + id + e, e);
        }
    }

    public List<Cathedra> findAll() throws ServiceException {
        try {
            List<Cathedra> cathedraList = cathedraRepository.findAll();
            DEBUG.debug("All cathedras was returned");
            return cathedraList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any cathedras" + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            cathedraRepository.delete(this.findById(id));
            DEBUG.debug(format("Cathedra with id - %d is removed", id));
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cant remove cathedra with id - " + id + e, e);
        }
    }

    public Cathedra createNewCathedra(CathedraForm cathedraForm){
        Cathedra cathedra = new Cathedra();
        cathedra.setName(cathedraForm.getName());
            cathedra.setFaculty(facultyRepository.findById(cathedraForm.getFacultyId()).get());
        return cathedra;
    }
}