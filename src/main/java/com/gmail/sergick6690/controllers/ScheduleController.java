package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
    private ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String startPage() {
        return "schedules/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("schedules", service.findAll());
        return "schedules/schedules";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("schedule", service.findById(id));
        return "schedules/show";
    }

    @PostMapping("/schedule")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("schedule", service.findById(id));
        return "schedules/show";
    }
}
