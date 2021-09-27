package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.CathedraForm;
import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.university.Cathedra;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/cathedras")
public class CathedraController {
    private CathedraService cathedraService;
    private FacultyService facultyService;

    public CathedraController(CathedraService cathedraService, FacultyService facultyService) {
        this.cathedraService = cathedraService;
        this.facultyService = facultyService;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("cathedraForm", new CathedraForm());
        return "cathedra/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("cathedras", cathedraService.findAll());
        return "cathedra/cathedras";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("cathedra", cathedraService.findById(id));
        return "cathedra/show";
    }

    @PostMapping("/cathedra")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("cathedra", cathedraService.findById(id));
        return "cathedra/show";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("cathedraForm") @Valid CathedraForm cathedraForm
            , BindingResult bindingResult, RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "cathedra/index";
        }
        Cathedra cathedra = createNewCathedra(cathedraForm);
        cathedraService.add(cathedra);
        attributes.addFlashAttribute("message", "Was added new cathedra - " + cathedra);
        return "redirect:/cathedras";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        cathedraService.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted cathedra with id - " + id);
        return "redirect:/cathedras";
    }

    private Cathedra createNewCathedra(CathedraForm cathedraForm) throws ServiceException {
        Cathedra cathedra = new Cathedra();
        cathedra.setName(cathedraForm.getName());
        cathedra.setFaculty(facultyService.findById(cathedraForm.getFacultyId()));
        return cathedra;
    }
}
