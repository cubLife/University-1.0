package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.CathedraRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Cathedra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class CathedraService {
    private CathedraRepository cathedraRepository;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public CathedraService(CathedraRepository cathedraRepository) {
        this.cathedraRepository = cathedraRepository;
    }

    public void add(Cathedra cathedra) throws ServiceException {
        if (cathedra == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            cathedraRepository.save(cathedra);
            DEBUG.debug((format("New cathedra - %s was added", cathedra.toString())));
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cant't add cathedra" + e, e);
        }
    }

    public Cathedra findById(int id) throws ServiceException {
        try {
            Cathedra cathedra = cathedraRepository.findById(id).get();
            DEBUG.debug(format("Cathedra with id - %d was returned", id));
            return cathedra;
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cathedra not found - " + id, e);
        }
    }

    public List<Cathedra> findAll() throws ServiceException {
        try {
            List<Cathedra> cathedraList = cathedraRepository.findAll();
            DEBUG.debug("All cathedras was returned");
            return cathedraList;
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any cathedras", e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            cathedraRepository.delete(this.findById(id));
            DEBUG.debug(format("Cathedra with id - %d is removed", id));
        } catch (Exception e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cant remove cathedra with id - " + id, e);
        }
    }
}