package com.gmail.sergick6690.controllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.SubjectForm;
import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.universityModels.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    private SubjectService subjectService;
    private ModelsCreator modelsCreator;

    @Autowired
    public SubjectController(SubjectService subjectService, ModelsCreator modelsCreator) {
        this.subjectService = subjectService;
        this.modelsCreator = modelsCreator;
    }

    @GetMapping()
    public String startPage(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/index";
    }

    @GetMapping("/all")
    public String showAll(Model model) throws ServiceException {
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects/subjects";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) throws ServiceException {
        model.addAttribute("subject", subjectService.findById(id));
        return "subjects/show";
    }

    @PostMapping("/subject")
    public String show(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("subject", subjectService.findById(id));
        return "subjects/show";
    }

    @PostMapping("/related")
    public String showSubjectsRelatedToAudience(@RequestParam("id") int id, Model model) throws ServiceException {
        model.addAttribute("subjects", subjectService.findAllSubjectRelatedToAudience(id));
        model.addAttribute("number", id - 1);
        return "subjects/related";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid SubjectForm subjectForm, RedirectAttributes attributes) throws ServiceException {
        Subject subject = modelsCreator.createNewSubject(subjectForm);
        subjectService.add(subject);
        attributes.addFlashAttribute("message", "Was added new subject - " + subject);
        return "redirect:/subjects";
    }

    @DeleteMapping("/remove/teacher")
    public String removeTeacher(@RequestParam("subjectId") int subjectId, RedirectAttributes attributes) throws ServiceException {
        subjectService.removeTeacher(subjectId);
        attributes.addFlashAttribute("message", "Was removed new teacher from subject with id - " + subjectId);
        return "redirect:/subjects";
    }

    @PostMapping("/assign/teacher")
    public String assignTeacher(@RequestParam("subjectId") int subjectId, @RequestParam("teacherId") int teacherId, RedirectAttributes attributes) throws ServiceException {
        subjectService.assignTeacher(subjectId, teacherId);
        attributes.addFlashAttribute("message", "Was assigned teacher with id - " + teacherId + " for subject with id - " + subjectId);
        return "redirect:/subjects";
    }

    @PostMapping("/change/teacher")
    public String changeTeacher(@RequestParam("subjectId") int subjectId, @RequestParam("teacherId") int teacherId, RedirectAttributes attributes) throws ServiceException {
        subjectService.changeTeacher(subjectId, teacherId);
        attributes.addFlashAttribute("message", "Was changed teacher on teacher with id - " + teacherId + " for subject with id - " + subjectId);
        return "redirect:/subjects";
    }

    @DeleteMapping("/delete")
    public String deleteSubject(@RequestParam("id") int id, RedirectAttributes attributes) throws ServiceException {
        subjectService.removeById(id);
        attributes.addFlashAttribute("message", "Was deleted subject with id - " + id);
        return "redirect:/subjects";
    }
}
