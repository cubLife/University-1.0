package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.university.Cathedra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cathedras")
public class CathedraController {
    private CathedraService service;

    @Autowired
    public CathedraController(CathedraService service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("cathedra", new Cathedra());
        return "cathedra/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("cathedras", service.findAll());
        return "cathedra/cathedras";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("cathedra", service.findById(id));
        return "cathedra/show";
    }

    @PostMapping("/cathedra")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("cathedra", service.findById(id));
        return "cathedra/show";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("cathedra") Cathedra cathedra, RedirectAttributes attributes) throws ServiceException {
        service.add(cathedra);
        attributes.addFlashAttribute("message", "Was added new cathedra - " + cathedra);
        return "redirect:/cathedras";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        service.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted cathedra with id - " + id);
        return "redirect:/cathedras";
    }
}
