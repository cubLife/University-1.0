package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.implementation.JdbcScheduleDAO;
import com.gmail.sergick6690.university.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService implements CrudMethods<Schedule> {
    private ScheduleDAO scheduleDAO;

    public ScheduleService(JdbcScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }


    @Override
    public void add(Schedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("Input Parameter can't be null");
        }
        scheduleDAO.add(schedule);
    }

    @Override
    public Schedule findById(int id) {
        return scheduleDAO.findById(id);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        scheduleDAO.removeById(id);
    }
}
