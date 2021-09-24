package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.ScheduleRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void add(Schedule schedule) throws ServiceException {
        if (schedule == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input Parameter can't be null");
        }
        try {
            scheduleRepository.save(schedule);
            DEBUG.debug((format("New schedule - %s was added", schedule.toString())));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't add schedule - " + schedule + e, e);
        }
    }

    public Schedule findById(int id) throws ServiceException {
        try {
            Schedule schedule = scheduleRepository.findById(id).get();
            DEBUG.debug(format("Item with id - %d was returned", id));
            return schedule;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Schedule not found - " + id + e, e);
        }
    }

    public List<Schedule> findAll() throws ServiceException {
        try {
            List<Schedule> scheduleList = scheduleRepository.findAll();
            DEBUG.debug("All schedules was returned");
            return scheduleList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any schedule " + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            scheduleRepository.delete(this.findById(id));
            DEBUG.debug(format("Schedule with id - %d is removed", id));
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove schedule with id - " + id + e, e);
        }
    }
}
