package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.GroupService;
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

    @GetMapping("/index")
    public String startPage() {
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
}
