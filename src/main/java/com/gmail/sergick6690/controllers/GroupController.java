package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.university.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private GroupService service;

    @Autowired
    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("group", new Group());
        return "groups/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("groups", service.findAll());
        return "groups/groups";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("group", service.findById(id));
        return "groups/show";
    }

    @PostMapping("/group")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("group", service.findById(id));
        return "groups/show";
    }

    @PostMapping("/related")
    public String showGroupsRelatedToCathedra(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("groups", service.findAllGroupsRelatedToCathedra(id));
        return "groups/related";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("group") Group group) throws ServiceException {
        service.add(group);
        return "redirect:/groups";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id) throws ServiceException {
        service.removeById(id);
        return "redirect:/groups";
    }
}