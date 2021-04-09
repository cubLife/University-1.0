package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Audience;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AudienceDAO extends CrudMethods<Audience> {
    @Override
    default void add(Audience obj) {
    }

    @Override
    default Audience findById(int id) throws NotImplementedException {
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