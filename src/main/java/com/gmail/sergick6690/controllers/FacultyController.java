package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.university.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/faculties")
public class FacultyController {
    private FacultyService service;

    @Autowired
    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculties/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("faculties", service.findAll());
        return "faculties/faculties";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("faculty", service.findById(id));
        return "faculties/show";
    }

    @PostMapping("/faculty")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("faculty", service.findById(id));
        return "faculties/show";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("faculty") Faculty faculty) throws ServiceException {
        service.add(faculty);
        return "redirect:/faculties";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id) throws ServiceException {
        service.removeById(id);
        return "redirect:/faculties";
    }
}
