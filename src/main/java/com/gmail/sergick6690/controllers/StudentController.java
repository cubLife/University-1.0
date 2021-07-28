package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.university.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute(new Student());
        return "students/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("students", service.findAll());
        return "students/students";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("student", service.findById(id));
        return "students/show";
    }

    @PostMapping("/student")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("student", service.findById(id));
        return "students/show";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("student") Student student, RedirectAttributes attributes) throws ServiceException {
        service.add(student);
        attributes.addFlashAttribute("message", "Was added new student - " + student);
        return "redirect:/students";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        service.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted student with id - " + id);
        return "redirect:/students";
    }

    @PostMapping("/assign/group")
    public String assignGroup(@RequestParam("studentId") int studentId, @RequestParam("groupId") int groupId, RedirectAttributes attributes) throws ServiceException {
        service.assignGroup(studentId, groupId);
        attributes.addFlashAttribute("message", "Was assigned group with id - " + groupId + " for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/remove/group")
    public String removeFromGroup(@RequestParam("studentId") int studentId, RedirectAttributes attributes) throws ServiceException {
        service.removeFromGroup(studentId);
        attributes.addFlashAttribute("message", "Was deleted student with id - " + studentId + " from group");
        return "redirect:/students";
    }

    @PostMapping("/change/group")
    public String changeGroup(@RequestParam("studentId") int studentId, @RequestParam("groupId") int groupId, RedirectAttributes attributes) throws ServiceException {
        service.changeGroup(studentId, groupId);
        attributes.addFlashAttribute("message", "Was changed group on group with id - " + groupId + " for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/assign/course")
    public String assignCourse(@RequestParam("studentId") int studentId, @RequestParam("course") int course, RedirectAttributes attributes) throws ServiceException {
        service.assignCourse(studentId, course);
        attributes.addFlashAttribute("message", "Was assigned course - " + course + " for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/remove/course")
    public String removeFromCourse(@RequestParam("studentId") int studentId, RedirectAttributes attributes) throws ServiceException {
        service.removeFromCourse(studentId);
        attributes.addFlashAttribute("message", "Was removed course for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/change/course")
    public String changeCourse(@RequestParam("studentId") int studentId, @RequestParam("course") int course, RedirectAttributes attributes) throws ServiceException {
        service.changeCourse(studentId, course);
        attributes.addFlashAttribute("message", "Was changed course on course - " + course + " for student with id - " + studentId);
        return "redirect:/students";
    }
}
