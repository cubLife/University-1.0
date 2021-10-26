package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.TeacherForm;
import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.universityModels.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherService teacherService;
    private ModelsCreator modelsCreator;

    @Autowired
    public TeacherController(TeacherService teacherService, ModelsCreator modelsCreator) {
        this.teacherService = teacherService;
        this.modelsCreator = modelsCreator;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("teacherForm", new TeacherForm());
        return "teacher/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("teachers", teacherService.findAll());
        return "teacher/teachers";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teacher/show";
    }

    @PostMapping("/teacher")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teacher/show";
    }

    @PostMapping("/equal/degree")
    public String showWithEqualDegree(@RequestParam("degree") String degree, Model model) throws ServiceException {
        model.addAttribute("count", teacherService.findTeachersCountWithEqualDegree(degree));
        model.addAttribute("degree", degree);
        return "teacher/equalDegree";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("teacherForm") @Valid TeacherForm teacherForm, BindingResult bindingResult, RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "teacher/index";
        }
        Teacher teacher = modelsCreator.createNewTeacher(teacherForm);
        teacherService.add(teacher);
        attributes.addFlashAttribute("message", "Was added new teacher - " + teacher);
        return "redirect:/teachers";
    }

    @DeleteMapping("/remove/schedule")
    public String removeSchedule(@RequestParam("teacherId") int id, RedirectAttributes attributes) throws ServiceException {
        teacherService.removeSchedule(id);
        attributes.addFlashAttribute("message", "Was removed schedule for teacher with id - " + id);
        return "redirect:/teachers";
    }

    @PostMapping("/assign/schedule")
    public String assignSchedule(@RequestParam("teacherId") int teacherId, @RequestParam("scheduleId") int scheduleId, RedirectAttributes attributes) throws ServiceException {
        teacherService.assignSchedule(teacherId, scheduleId);
        attributes.addFlashAttribute("message", "Was assigned schedule with id - " + scheduleId + " for teacher with id - " + teacherId);
        return "redirect:/teachers";
    }

    @PostMapping("/change/schedule")
    public String changeSchedule(@RequestParam("teacherId") int teacherId, @RequestParam("scheduleId") int scheduleId, RedirectAttributes attributes) throws ServiceException {
        teacherService.changeSchedule(teacherId, scheduleId);
        attributes.addFlashAttribute("message", "Schedule was changed on schedule with id - " + scheduleId + " for teacher with id " + teacherId);
        return "redirect:/teachers";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        teacherService.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted student with id - " + id);
        return "redirect:/teachers";
    }
}
