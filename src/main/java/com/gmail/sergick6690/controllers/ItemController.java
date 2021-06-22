package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String startPage() {
        return "items/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("items", service.findAll());
        return "items/items";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("item", service.findById(id));
        return "items/show";
    }

    @PostMapping("/item")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("item", service.findById(id));
        return "items/show";
    }
}
