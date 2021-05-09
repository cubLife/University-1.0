package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcGroupDAO;
import com.gmail.sergick6690.university.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class GroupService implements CrudMethods<Group> {
    private JdbcGroupDAO groupDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public GroupService(JdbcGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public void add(Group group) throws ServiceException {
        if (group == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            groupDAO.add(group);
            DEBUG.debug((format("New group - %s was added", group.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Group findById(int id) throws ServiceException {
        try {
            Group group = groupDAO.findById(id);
            DEBUG.debug(format("Group with id - %d was returned", id));
            return group;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Group> findAll() throws ServiceException {
        try {
            List<Group> groupList = groupDAO.findAll();
            DEBUG.debug("All groups was returned");
            return groupList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            groupDAO.removeById(id);
            DEBUG.debug(format("Group with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public List<Group> findAllGroupsRelatedToCathedra(int id) throws ServiceException {
        try {
            List<Group> groupList = groupDAO.findAllGroupsRelatedToCathedra(id);
            DEBUG.debug(String.format("Was returned %d related to cathedra with id - %d", groupList.size(), id));
            return groupList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}
