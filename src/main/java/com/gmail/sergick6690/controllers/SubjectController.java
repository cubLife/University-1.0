package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.university.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SubjectController {
    private SubjectService service;
    private AudienceService audienceService;

    @Autowired
    public SubjectController(SubjectService service, AudienceService audienceService) {
        this.service = service;
        this.audienceService = audienceService;
    }

    @GetMapping("subjects/index")
    public String startPage() {
        return "subjects/index";
    }

    @GetMapping("all/subjects")
    public String showAll(Model model) {
        List<Subject> subjects;
        try {
            subjects = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("subjects", subjects);
        return "subjects/subjects";
    }

    @GetMapping("/subjects/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Subject subject = null;
        try {
            subject = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("subject", subject);
        return "subjects/show";
    }

    @PostMapping("show/subject")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("subject", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.getMessage());
            return "errorPage";
        }
        return "subjects/show";
    }

    @PostMapping("/subjects/related")
    public String showSubjectsRelatedToAudience(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("subjects", service.findAllSubjectRelatedToAudience(id));
            model.addAttribute("number", audienceService.findById(id).getNumber());
        } catch (ServiceException e) {
            model.addAttribute("massage", e.getMessage());
            return "errorPage";
        }
        return "subjects/related";
    }
}
