package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.university.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {
    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/students/index")
    public String startPage() {
        return "students/index";
    }

    @GetMapping("all/students")
    public String showAll(Model model) {
        List<Student> students;
        try {
            students = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("students", students);
        return "students/students";
    }

    @GetMapping("/students/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Student student = null;
        try {
            student = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("student", student);
        return "students/show";
    }

    @PostMapping("show/student")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("student", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.getMessage());
            return "errorPage";
        }
        return "students/show";
    }
}
