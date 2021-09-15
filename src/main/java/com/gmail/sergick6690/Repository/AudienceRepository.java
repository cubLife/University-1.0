package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Audience;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AudienceRepository extends GenericRepository<Audience> {
    @Override
    default void add(Audience obj) throws RepositoryException {
    }

    @Override
    default Audience findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Audience> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }
}