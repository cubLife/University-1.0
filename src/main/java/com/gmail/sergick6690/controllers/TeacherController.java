package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.university.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String startPage() {
        return "teacher/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("teachers",service.findAll());
        return "teacher/teachers";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("teacher", service.findById(id));
        return "teacher/show";
    }

    @PostMapping("/teacher")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
            model.addAttribute("teacher", service.findById(id));
        return "teacher/show";
    }

    @PostMapping("/equal/degree")
    public String showWithEqualDegree(@RequestParam("degree") String degree, Model model) throws ServiceException {
            model.addAttribute("count", service.findTeachersCountWithEqualDegree(degree));
        model.addAttribute("degree", degree);
        return "teacher/equalDegree";
    }
}
