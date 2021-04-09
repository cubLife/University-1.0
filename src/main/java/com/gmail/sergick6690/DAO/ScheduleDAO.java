package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Schedule;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;

import java.util.List;

public interface ScheduleDAO  extends CrudMethods<Schedule> {
    @Override
    default void add(Schedule obj) {
    }

    @Override
    default Schedule findById(int id) throws NotImplementedException {
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
