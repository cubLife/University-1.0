package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Schedule;

import java.util.List;

public interface ScheduleDAO {
    public void addSchedule(Schedule schedule);

    public Schedule findScheduleByID(int id);

    public List<Schedule> findAllSchedules();

    public void removeScheduleById(int id);
}
