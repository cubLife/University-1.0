package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Schedule;

import java.util.List;

public interface ScheduleRepository extends GenericRepository<Schedule> {
    @Override
    default void add(Schedule obj) throws RepositoryException {
    }

    @Override
    default Schedule findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Schedule> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }
}
