package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Audience;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public interface AudienceDAO extends CrudMethods<Audience> {
    @Override
    default void add(Audience obj) {
    }

    @Override
    default Audience findById(int id) throws SQLException {
        return null;
    }

    @Override
    default List<Audience> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }
}
