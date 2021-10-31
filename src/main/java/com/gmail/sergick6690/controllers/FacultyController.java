package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.universityModels.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/faculties")
public class FacultyController {
    private FacultyService service;

    @Autowired
    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("faculty", new Faculty());
        return "faculties/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("faculties", service.findAll());
        return "faculties/faculties";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("faculty", service.findById(id));
        return "faculties/show";
    }

    @PostMapping("/faculty")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("faculty", service.findById(id));
        return "faculties/show";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("faculty") @Valid Faculty faculty, BindingResult bindingResult, RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "faculties/index";
        }
        service.add(faculty);
        attributes.addFlashAttribute("message", "Was added new faculty - " + faculty);
        return "redirect:/faculties";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        service.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted faculty with id - " + id);
        return "redirect:/faculties";
    }
}
