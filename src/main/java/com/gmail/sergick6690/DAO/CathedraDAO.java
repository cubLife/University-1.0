package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Cathedra;

import java.sql.SQLException;
import java.util.List;

public interface CathedraDAO  extends CRUD<Cathedra>{
    @Override
    default void add(Cathedra obj) {

    }

    @Override
    default Cathedra findById(int id) throws Exception {
        return null;
    }

    @Override
    default List<Cathedra> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {

    }
}
