package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    private SubjectService service;
    private AudienceService audienceService;

    @Autowired
    public SubjectController(SubjectService service, AudienceService audienceService) {
        this.service = service;
        this.audienceService = audienceService;
    }

    @GetMapping("/index")
    public String startPage() {
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
}
