package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.university.Cathedra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CathedraController {
    private CathedraService service;

    @Autowired
    public CathedraController(CathedraService service) {
        this.service = service;
    }

    @GetMapping("cathedras/index")
    public String startPage() {
        return "cathedra/index";
    }

    @GetMapping("all/cathedras")
    public String showAll(Model model) {
        List<Cathedra> cathedras;
        try {
            cathedras = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("cathedras", cathedras);
        return "cathedra/cathedras";
    }

    @GetMapping("/cathedras/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Cathedra cathedra = null;
        try {
            cathedra = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("cathedra", cathedra);
        return "cathedra/show";
    }

    @PostMapping("show/cathedra")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("cathedra", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        return "cathedra/show";
    }
}
