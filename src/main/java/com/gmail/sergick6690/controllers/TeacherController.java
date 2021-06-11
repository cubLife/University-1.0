package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.university.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TeacherController {
    private TeacherService service;

    @Autowired
    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping("teachers/index")
    public String startPage() {
        return "teacher/index";
    }

    @GetMapping("/all/teachers")
    public String showAll(Model model) {
        List<Teacher> teachers;
        try {
            teachers = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("teachers", teachers);
        return "teacher/teachers";
    }

    @GetMapping("/teachers/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Teacher teacher = null;
        try {
            teacher = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("teacher", teacher);
        return "teacher/show";
    }

    @PostMapping("/show/teacher")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("teacher", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        return "teacher/show";
    }

    @PostMapping("/equal/degree")
    public String showWithEqualDegree(@RequestParam("degree") String degree, Model model) {
        try {
            model.addAttribute("count", service.findTeachersCountWithEqualDegree(degree));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("degree", degree);
        return "teacher/equalDegree";
    }
}
