package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Schedule;

import java.util.List;

public interface ScheduleDAO  extends GenericDao<Schedule> {
    @Override
    default void add(Schedule obj) throws DaoException {
    }

    @Override
    default Schedule findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Schedule> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }
}
