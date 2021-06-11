package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.university.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AudienceController {
    private AudienceService service;

    @Autowired
    public AudienceController(AudienceService service) {
        this.service = service;
    }

    @GetMapping("audiences/index")
    public String startPage() {
        return "audiences/index";
    }

    @GetMapping("/all/audiences")
    public String showAll(Model model) {
        List<Audience> audiences;
        try {
            audiences = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("audiences", audiences);
        return "audiences/audiences";
    }

    @GetMapping("/audiences/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Audience audience = null;
        try {
            audience = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("audience", audience);
        return "audiences/show";
    }

    @PostMapping("/show/audience")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("audience", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        return "audiences/show";
    }
}
