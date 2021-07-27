package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.ItemService;
import com.gmail.sergick6690.university.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("item", new Item());
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

    @PostMapping("/add")
    public String add(@RequestParam("subjectId") int subjectId, @RequestParam("day") String day, @RequestParam("hour") int hour, @RequestParam("audienceId") int audienceId
            , @RequestParam("duration") int duration, @RequestParam("scheduleId") int scheduleId, RedirectAttributes attributes) throws ServiceException {
        Item item = new Item(subjectId, day, hour, audienceId, duration, scheduleId);
        service.add(item);
        attributes.addFlashAttribute("message", "Was added new item - " + item);
        return "redirect:/items";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        service.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted item with id - " + id);
        return "redirect:/items";
    }
}
