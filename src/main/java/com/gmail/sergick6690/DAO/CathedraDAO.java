package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Cathedra;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;

import java.util.List;

public interface CathedraDAO extends CrudMethods<Cathedra> {
    @Override
    default void add(Cathedra obj) {
    }

    @Override
    default Cathedra findById(int id) throws NotImplementedException {
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
