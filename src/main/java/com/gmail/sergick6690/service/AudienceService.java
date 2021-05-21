package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.GenericDao;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import com.gmail.sergick6690.university.Audience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class AudienceService implements GenericDao<Audience> {
    private JdbcAudienceDAO audienceDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public AudienceService(JdbcAudienceDAO audienceDAO) {
        this.audienceDAO = audienceDAO;
    }

    @Override
    public void add(Audience audience) throws ServiceException {
        if (audience == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            audienceDAO.add(audience);
            DEBUG.debug(format("New audience - %s is added", audience.toString()));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Audience findById(int id) throws ServiceException {
        try {
            Audience audience = audienceDAO.findById(id);
            DEBUG.debug(format("Audience with id - %d was returned", id));
            return audience;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Audience> findAll() throws ServiceException {
        try {
            List<Audience> audienceList = audienceDAO.findAll();
            DEBUG.debug("All audiences was returned");
            return audienceList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            audienceDAO.removeById(id);
            DEBUG.debug(format("Audience with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}
