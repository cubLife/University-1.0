package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.university.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private GroupService service;
    private ScheduleService scheduleService;
    private CathedraService cathedraService;

    @Autowired
    public GroupController(GroupService service, ScheduleService scheduleService, CathedraService cathedraService) {
        this.service = service;
        this.scheduleService = scheduleService;
        this.cathedraService = cathedraService;
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
    public String add(@ModelAttribute("group") Group group, @RequestParam("scheduleId") int scheduleId
            , @RequestParam("cathedraId") int cathedraId, RedirectAttributes attributes) throws ServiceException {
        service.add(setScheduleAndCathedra(group, scheduleId, cathedraId));
        attributes.addFlashAttribute("message", "Was added new group - " + group);
        return "redirect:/groups";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        service.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted group with id - " + id);
        return "redirect:/groups";
    }

    private Group setScheduleAndCathedra(Group group, int scheduleId, int cathedraId) throws ServiceException {
        Schedule schedule = scheduleService.findById(scheduleId);
        Cathedra cathedra = cathedraService.findById(cathedraId);
        group.setSchedule(schedule);
        group.setCathedra(cathedra);
        return group;
    }
}

