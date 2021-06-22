package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/audiences")
public class AudienceController {
    private AudienceService service;

    @Autowired
    public AudienceController(AudienceService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String startPage() {
        return "audiences/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("audiences", service.findAll());
        return "audiences/audiences";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("audience", service.findById(id));
        return "audiences/show";
    }

    @PostMapping("/audience")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("audience", service.findById(id));
        return "audiences/show";
    }
}
