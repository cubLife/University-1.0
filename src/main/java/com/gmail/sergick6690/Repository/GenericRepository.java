package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.exceptions.ServiceException;

import java.util.List;

public interface GenericRepository<T> {

    public void add(T obj) throws RepositoryException, ServiceException;

    public T findById(int id) throws RepositoryException, ServiceException;

    public List<T> findAll() throws RepositoryException, ServiceException;

    public void removeById(int id) throws ServiceException, RepositoryException;
}
