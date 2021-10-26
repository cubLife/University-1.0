package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.ItemForm;
import com.gmail.sergick6690.service.ItemService;
import com.gmail.sergick6690.universityModels.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;
    private ModelsCreator modelsCreator;

    @Autowired
    public ItemController(ItemService itemService, ModelsCreator modelsCreator) {
        this.itemService = itemService;
        this.modelsCreator = modelsCreator;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("itemForm", new ItemForm());
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
    public String add(@ModelAttribute("itemForm") @Valid ItemForm itemForm, BindingResult bindingResult, RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "items/index";
        }
        Item item = modelsCreator.createNewItem(itemForm);
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
}
