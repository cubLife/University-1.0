package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.university.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    private SubjectService service;

    @Autowired
    public SubjectController(SubjectService service, AudienceService audienceService) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("subjects", service.findAll());
        return "subjects/subjects";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("subject", service.findById(id));
        return "subjects/show";
    }

    @PostMapping("/subject")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("subject", service.findById(id));
        return "subjects/show";
    }

    @PostMapping("/related")
    public String showSubjectsRelatedToAudience(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("subjects", service.findAllSubjectRelatedToAudience(id));
        model.addAttribute("number", id - 1);
        return "subjects/related";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("subject") Subject subject) throws ServiceException {
        service.add(subject);
        return "redirect:/subjects";
    }

    @DeleteMapping("/remove/teacher")
    public String removeTeacher(@RequestParam("subjectId") int subjectId) throws ServiceException {
        service.removeTeacher(subjectId);
        return "redirect:/subjects";
    }

    @PostMapping("/assign/teacher")
    public String assignTeacher(@RequestParam("subjectId") int subjectId, @RequestParam("teacherId") int teacherId) throws ServiceException {
        service.assignTeacher(subjectId, teacherId);
        return "redirect:/subjects";
    }

    @PostMapping("/change/teacher")
    public String changeTeacher(@RequestParam("subjectId") int subjectId, @RequestParam("teacherId") int teacherId) throws ServiceException {
        service.changeTeacher(subjectId, teacherId);
        return "redirect:/subjects";
    }

    @DeleteMapping("/delete")
    public String deleteSubject(@RequestParam("id") int id) throws ServiceException {
        service.removeById(id);
        return "redirect:/subjects";
    }
}
