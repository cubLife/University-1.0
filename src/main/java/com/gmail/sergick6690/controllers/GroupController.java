package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.university.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GroupController {
    private GroupService service;

    @Autowired
    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping("/groups/index")
    public String startPage() {
        return "groups/index";
    }

    @GetMapping("all/groups")
    public String showAll(Model model) {
        List<Group> groups;
        try {
            groups = service.findAll();
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("groups", groups);
        return "groups/groups";
    }

    @GetMapping("/groups/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        Group group = null;
        try {
            group = service.findById(id);
        } catch (ServiceException e) {
            model.addAttribute("massage", e.toString());
            return "errorPage";
        }
        model.addAttribute("group", group);
        return "groups/show";
    }

    @PostMapping("show/group")
    public String show(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("group", service.findById(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.getMessage());
            return "errorPage";
        }
        return "groups/show";
    }

    @PostMapping("/groups/related")
    public String showGroupsRelatedToCathedra(@RequestParam("id") int id, Model model) {
        try {
            model.addAttribute("groups", service.findAllGroupsRelatedToCathedra(id));
        } catch (ServiceException e) {
            model.addAttribute("massage", e.getMessage());
            return "errorPage";
        }
        return "groups/related";
    }
}
