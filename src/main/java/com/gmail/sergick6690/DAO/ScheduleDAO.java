package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDAO {
    public void addSchedule(Schedule schedule);

    public Schedule findScheduleByID(int id) throws SQLException;

    public List<Schedule> findAllSchedules();

    public void removeScheduleById(int id);
}
