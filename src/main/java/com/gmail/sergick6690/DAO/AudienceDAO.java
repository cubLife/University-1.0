package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Audience;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AudienceDAO extends CrudMethods<Audience> {
    @Override
    default void add(Audience obj) throws DaoException{
    }

    @Override
    default Audience findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Audience> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }
}