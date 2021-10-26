package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.GroupForm;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.universityModels.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private GroupService service;
    private ModelsCreator modelsCreator;

    @Autowired
    public GroupController(GroupService service, ModelsCreator modelsCreator) {
        this.service = service;
        this.modelsCreator = modelsCreator;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("groupForm", new GroupForm());
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
    public String add(@ModelAttribute("groupForm") @Valid GroupForm groupForm, BindingResult bindingResult, RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "groups/index";
        }
        Group group = modelsCreator.createNewGroup(groupForm);
        service.add(group);
        attributes.addFlashAttribute("message", "Was added new group - " + group);
        return "redirect:/groups";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        service.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted group with id - " + id);
        return "redirect:/groups";
    }
}

