package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Audience;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AudienceDAO extends CRUD<Audience> {
    @Override
    default void add(Audience obj) {

    }

    @Override
    default Audience findById(int id) throws Exception {
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
