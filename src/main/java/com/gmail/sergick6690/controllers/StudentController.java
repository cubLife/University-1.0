package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.StudentForm;
import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.universityModels.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute(new StudentForm());
        return "students/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("students", studentService.findAll());
        return "students/students";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("student", studentService.findById(id));
        return "students/show";
    }

    @PostMapping("/student")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("student", studentService.findById(id));
        return "students/show";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("studentForm") @Valid StudentForm studentForm, BindingResult bindingResult, RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "students/index";
        }
        Student student = studentService.createNewStudent(studentForm);
        studentService.add(student);
        attributes.addFlashAttribute("message", "Was added new student - " + student);
        return "redirect:/students";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        studentService.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted student with id - " + id);
        return "redirect:/students";
    }

    @PostMapping("/assign/group")
    public String assignGroup(@RequestParam("studentId") int studentId, @RequestParam("groupId") int groupId, RedirectAttributes attributes) throws ServiceException {
        studentService.assignGroup(studentId, groupId);
        attributes.addFlashAttribute("message", "Was assigned group with id - " + groupId + " for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/remove/group")
    public String removeFromGroup(@RequestParam("studentId") int studentId, RedirectAttributes attributes) throws ServiceException {
        studentService.removeFromGroup(studentId);
        attributes.addFlashAttribute("message", "Was deleted student with id - " + studentId + " from group");
        return "redirect:/students";
    }

    @PostMapping("/change/group")
    public String changeGroup(@RequestParam("studentId") int studentId, @RequestParam("groupId") int groupId, RedirectAttributes attributes) throws ServiceException {
        studentService.changeGroup(studentId, groupId);
        attributes.addFlashAttribute("message", "Was changed group on group with id - " + groupId + " for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/assign/course")
    public String assignCourse(@RequestParam("studentId") int studentId, @RequestParam("course") int course, RedirectAttributes attributes) throws ServiceException {
        studentService.assignCourse(studentId, course);
        attributes.addFlashAttribute("message", "Was assigned course - " + course + " for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/remove/course")
    public String removeFromCourse(@RequestParam("studentId") int studentId, RedirectAttributes attributes) throws ServiceException {
        studentService.removeFromCourse(studentId);
        attributes.addFlashAttribute("message", "Was removed course for student with id - " + studentId);
        return "redirect:/students";
    }

    @PostMapping("/change/course")
    public String changeCourse(@RequestParam("studentId") int studentId, @RequestParam("course") int course, RedirectAttributes attributes) throws ServiceException {
        studentService.changeCourse(studentId, course);
        attributes.addFlashAttribute("message", "Was changed course on course - " + course + " for student with id - " + studentId);
        return "redirect:/students";
    }
}
