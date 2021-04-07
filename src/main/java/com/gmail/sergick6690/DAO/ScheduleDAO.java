package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDAO  extends CrudMethods<Schedule> {
    @Override
    default void add(Schedule obj) {
    }

    @Override
    default Schedule findById(int id) throws SQLException {
        return null;
    }

    @Override
    default List<Schedule> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }
}
