package com.gmail.sergick6690.DAO;

import java.sql.SQLException;
import java.util.List;

public interface CrudMethods<T> {

    public void add(T obj);

    public T findById(int id) throws SQLException;

    public List<T> findAll();

    public void removeById(int id);
}
