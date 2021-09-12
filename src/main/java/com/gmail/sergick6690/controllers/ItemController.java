package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.service.ItemService;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.university.Audience;
import com.gmail.sergick6690.university.Item;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;
    private AudienceService audienceService;
    private SubjectService subjectService;
    private ScheduleService scheduleService;

    @Autowired
    public ItemController(ItemService itemService, AudienceService audienceService, SubjectService subjectService, ScheduleService scheduleService) {
        this.itemService = itemService;
        this.audienceService = audienceService;
        this.subjectService = subjectService;
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("item", new Item());
        return "items/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("items", itemService.findAll());
        return "items/items";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("item", itemService.findById(id));
        return "items/show";
    }

    @PostMapping("/item")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("item", itemService.findById(id));
        return "items/show";
    }

    @PostMapping("/add")
    public String add(@RequestParam("subjectId") int subjectId, @RequestParam("day") String day, @RequestParam("hour") int hour, @RequestParam("audienceId") int audienceId
            , @RequestParam("duration") int duration, @RequestParam("scheduleId") int scheduleId, RedirectAttributes attributes) throws ServiceException {
        Item item = createNewItem(subjectId, day, hour, audienceId, duration, scheduleId);
        itemService.add(item);
        attributes.addFlashAttribute("message", "Was added new item - " + item);
        return "redirect:/items";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        itemService.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted item with id - " + id);
        return "redirect:/items";
    }

    private Item createNewItem(int subjectId, String day, int hour, int audienceId, int duration, int scheduleId) throws ServiceException {
        Subject subject = subjectService.findById(subjectId);
        Audience audience = audienceService.findById(audienceId);
        Schedule schedule = scheduleService.findById(scheduleId);
        return new Item(subject, day, hour, audience, duration, schedule);
    }
}
