package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class ScheduleService {
    private ScheduleDAO scheduleDAO;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public ScheduleService(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    public void add(Schedule schedule) throws ServiceException {
        if (schedule == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input Parameter can't be null");
        }
        try {
            scheduleDAO.add(schedule);
            DEBUG.debug((format("New schedule - %s was added", schedule.toString())));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public Schedule findById(int id) throws ServiceException {
        try {
            Schedule schedule = scheduleDAO.findById(id);
            DEBUG.debug(format("Item with id - %d was returned", id));
            return schedule;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public List<Schedule> findAll() throws ServiceException {
        try {
            List<Schedule> scheduleList = scheduleDAO.findAll();
            DEBUG.debug("All schedules was returned");
            return scheduleList;
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            scheduleDAO.removeById(id);
            DEBUG.debug(format("Schedule with id - %d is removed", id));
        } catch (DaoException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}
