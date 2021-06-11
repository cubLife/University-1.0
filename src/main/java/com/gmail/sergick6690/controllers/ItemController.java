package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ItemService;
import com.gmail.sergick6690.university.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ItemController {
    private ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("items/index")
    public String startPage() {
        return "items/index";
    }

    @GetMapping("all/items")
    public String showAll(Model model) {
        List<Item> items;
        try {
            items = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("items", items);
        return "items/items";
    }

    @GetMapping("/items/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Item item;
        try {
            item = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("item", item);
        return "items/show";
    }

    @PostMapping("show/item")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("item", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.getMessage());
            return "errorPage";
        }
        return "items/show";
    }
}
