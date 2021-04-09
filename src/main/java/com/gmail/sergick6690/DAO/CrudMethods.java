package com.gmail.sergick6690.DAO;

import org.apache.maven.surefire.shared.lang3.NotImplementedException;

import java.sql.SQLException;
import java.util.List;

public interface CrudMethods<T> {

    public void add(T obj);

    public T findById(int id) throws NotImplementedException;

    public List<T> findAll();

    public void removeById(int id);
}
