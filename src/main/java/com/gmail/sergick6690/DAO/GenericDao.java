package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;

import java.util.List;

public interface GenericDao<T> {

    public void add(T obj) throws DaoException, ServiceException;

    public T findById(int id) throws DaoException, ServiceException;

    public List<T> findAll() throws DaoException, ServiceException;

    public void removeById(int id) throws DaoException, ServiceException;
}
