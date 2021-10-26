package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.SubjectForm;
import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.universityModels.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dev/subjects", produces = {"application/xml", "application/json"})
public class SubjectRestController {
    private SubjectService subjectService;
    private ModelsCreator modelsCreator;

    @Autowired
    public SubjectRestController(SubjectService subjectService, ModelsCreator modelsCreator) {
        this.subjectService = subjectService;
        this.modelsCreator = modelsCreator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subject addSubject(@RequestBody SubjectForm subjectForm) throws ServiceException {
        Subject subject = modelsCreator.createNewSubject(subjectForm);
        return subject;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Subject> showAllSubjects() throws ServiceException {
        return subjectService.findAll();
    }

    @GetMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public Subject showSubjectById(@PathVariable int subjectId) throws ServiceException {
        return subjectService.findById(subjectId);
    }

    @PutMapping("/assign/teacher")
    @ResponseStatus(HttpStatus.OK)
    public void assignTeacher(@RequestParam("subjectId") int subjectId, @RequestParam("teacherId") int teacherId) throws ServiceException {
        subjectService.assignTeacher(subjectId, teacherId);
    }

    @PutMapping("/remove/teacher")
    public void removeTeacher(@RequestParam("subjectId") int subjectId) throws ServiceException {
        subjectService.removeTeacher(subjectId);
    }

    @PutMapping("/change/teacher")
    @ResponseStatus(HttpStatus.OK)
    public void changeTeacher(@RequestParam("subjectId") int subjectId, @RequestParam("teacherId") int teacherId) throws ServiceException {
        subjectService.changeTeacher(subjectId, teacherId);
    }

    @GetMapping("/related/{audienceId}")
    public List<Subject> showSubjectsRelatedToAudience(@PathVariable("audienceId") int audienceId) throws ServiceException {
        return subjectService.findAllSubjectRelatedToAudience(audienceId);
    }

    @DeleteMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeSubject(@PathVariable int subjectId) throws ServiceException {
        subjectService.removeById(subjectId);
    }
}
