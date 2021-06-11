package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.university.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ScheduleController {
    private ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping("schedules/index")
    public String startPage() {
        return "schedules/index";
    }

    @GetMapping("all/schedules")
    public String showAll(Model model) {
        List<Schedule> schedules;
        try {
            schedules = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("schedules", schedules);
        System.out.println(schedules);
        return "schedules/schedules";
    }

    @GetMapping("/schedules/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Schedule schedule;
        try {
            schedule = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("schedule", schedule);
        return "schedules/show";
    }

    @PostMapping("show/schedule")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("schedule", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        return "schedules/show";
    }
}
