package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.university.Audience;
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

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("audience", new Audience());
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

    @PostMapping("/add")
    public String add(@ModelAttribute("audience") Audience audience) throws ServiceException {
        service.add(audience);
        return "redirect:/audiences";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id) throws ServiceException {
        service.removeById(id);
        return "redirect:/audiences";
    }
}
