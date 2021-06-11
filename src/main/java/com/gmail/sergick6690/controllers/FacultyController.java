package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.university.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FacultyController {
    private FacultyService service;

    @Autowired
    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping("faculties/index")
    public String startPage() {
        return "faculties/index";
    }

    @GetMapping("all/faculties")
    public String showAll(Model model) {
        List<Faculty> faculties;
        try {
            faculties = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("faculties", faculties);
        return "faculties/faculties";
    }

    @GetMapping("/faculties/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Faculty faculty = null;
        try {
            faculty = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("faculty", faculty);
        return "faculties/show";
    }

    @PostMapping("show/faculty")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("faculty", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        return "faculties/show";
    }
}
