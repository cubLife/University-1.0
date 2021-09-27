package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.AudienceRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Audience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class AudienceService {
    private AudienceRepository audienceRepository;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public AudienceService(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }

    public void add(Audience audience) throws ServiceException {
        if (audience == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            audienceRepository.save(audience);
            DEBUG.debug(format("New audience - %s is added", audience.toString()));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cant't add audience" + e, e);
        }
    }

    public Audience findById(int id) throws ServiceException {
        try {
            Audience audience = audienceRepository.findById(id).get();
            DEBUG.debug(format("Audience with id - %d was returned", id));
            return audience;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any audience with id - " + id + e, e);
        }
    }

    public List<Audience> findAll() throws ServiceException {
        try {
            List<Audience> audienceList = audienceRepository.findAll();
            DEBUG.debug("All audiences was returned");
            return audienceList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any audiences " + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            audienceRepository.delete(this.findById(id));
            DEBUG.debug(format("Audience with id - %d is removed", id));
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove audience with id - " + id + e, e);
        }
    }
}
