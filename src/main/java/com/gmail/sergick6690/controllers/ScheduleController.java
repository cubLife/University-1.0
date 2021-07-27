package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.university.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
    private ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("schedule", new Schedule());
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

    @PostMapping("/add")
    public String add(@ModelAttribute("schedule") Schedule schedule, RedirectAttributes attributes) throws ServiceException {
        service.add(schedule);
        attributes.addFlashAttribute("message", "Was added new schedule - " + new Schedule());
        return "redirect:/schedules";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        service.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted schedule with id - " + id);
        return "redirect:/schedules";
    }
}
