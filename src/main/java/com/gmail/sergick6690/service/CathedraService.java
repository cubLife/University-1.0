package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcCathedraDAO;
import com.gmail.sergick6690.university.Cathedra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class CathedraService implements CrudMethods<Cathedra> {
    private JdbcCathedraDAO cathedraDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public CathedraService(JdbcCathedraDAO cathedraDAO) {
        this.cathedraDAO = cathedraDAO;
    }

    @Override
    public void add(Cathedra cathedra) throws ServiceException {
        if (cathedra == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            cathedraDAO.add(cathedra);
            DEBUG.debug((format("New cathedra - %s was added", cathedra.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Cathedra findById(int id) throws ServiceException {
        try {
            Cathedra cathedra = cathedraDAO.findById(id);
            DEBUG.debug(format("Cathedra with id - %d was returned", id));
            return cathedra;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Cathedra> findAll() throws ServiceException {
        try {
            List<Cathedra> cathedraList = cathedraDAO.findAll();
            DEBUG.debug("All cathedras was returned");
            return cathedraList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            cathedraDAO.removeById(id);
            DEBUG.debug(format("Cathedra with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}