package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.universityModels.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dev/schedules", produces = {"application/xml", "application/json"})
public class ScheduleRestController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public Schedule addSchedule(@RequestBody Schedule schedule) throws ServiceException {
        scheduleService.add(schedule);
        return schedule;
    }

    @GetMapping()
    public List<Schedule> showAllSchedules() throws ServiceException {
        return scheduleService.findAll();
    }

    @GetMapping("/{scheduleId}")
    public Schedule showScheduleById(@PathVariable int scheduleId) throws ServiceException {
        return scheduleService.findById(scheduleId);
    }

    @DeleteMapping("/{scheduleId}")
    public void deleteScheduleById(@PathVariable int scheduleId) throws ServiceException {
        scheduleService.removeById(scheduleId);
    }
}
