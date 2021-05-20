package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.GenericDao;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.implementation.JdbcItemDAO;
import com.gmail.sergick6690.university.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class ItemService implements GenericDao<Item> {
    private JdbcItemDAO itemDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public ItemService(JdbcItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void add(Item item) throws ServiceException {
        if (item == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            itemDAO.add(item);
            DEBUG.debug((format("New item - %s was added", item.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Item findById(int id) throws ServiceException {
        try {
            Item item = itemDAO.findById(id);
            DEBUG.debug(format("Item with id - %d was returned", id));
            return item;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Item> findAll() throws ServiceException {
        try {
            List<Item> itemList = itemDAO.findAll();
            DEBUG.debug("All items was returned");
            return itemList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeById(int id) throws ServiceException {
        try {
            itemDAO.removeById(id);
            DEBUG.debug(format("Item with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}