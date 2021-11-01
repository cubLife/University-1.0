package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.universityModels.Schedule;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/schedules", produces = {"application/xml", "application/json"})
public class ScheduleRestController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Add new schedule")
    public Schedule addSchedule(@RequestBody Schedule schedule) throws ServiceException {
        scheduleService.add(schedule);
        return schedule;
    }

    @GetMapping("/list")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Get all schedules")
    public List<Schedule> showAllSchedules() throws ServiceException {
        return scheduleService.findAll();
    }

    @GetMapping("/{schedule-id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Get schedule by id")
    public Schedule showScheduleById(@PathVariable("schedule-id") int scheduleId) throws ServiceException {
        return scheduleService.findById(scheduleId);
    }

    @DeleteMapping("/{schedule-id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Get schedule by id")
    public void deleteScheduleById(@PathVariable("schedule-id") int scheduleId) throws ServiceException {
        scheduleService.removeById(scheduleId);
    }
}
