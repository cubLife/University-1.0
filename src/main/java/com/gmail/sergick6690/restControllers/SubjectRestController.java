package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.SubjectForm;
import com.gmail.sergick6690.service.SubjectService;
import com.gmail.sergick6690.universityModels.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/subjects", produces = {"application/xml", "application/json"})
public class SubjectRestController {
    private SubjectService subjectService;

    @Autowired
    public SubjectRestController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subject addSubject(@RequestBody SubjectForm subjectForm) throws ServiceException {
        Subject subject = subjectService.createNewSubject(subjectForm);
        subjectService.add(subject);
        return subject;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Subject> showAllSubjects() throws ServiceException {
        return subjectService.findAll();
    }

    @GetMapping("/{subject-id}")
    @ResponseStatus(HttpStatus.OK)
    public Subject showSubjectById(@PathVariable("subject-id") int subjectId) throws ServiceException {
        return subjectService.findById(subjectId);
    }

    @PutMapping("/assign/teacher")
    @ResponseStatus(HttpStatus.OK)
    public void assignTeacher(@RequestParam("subject-id") int subjectId, @RequestParam("teacher-id") int teacherId) throws ServiceException {
        subjectService.assignTeacher(subjectId, teacherId);
    }

    @PutMapping("/remove/teacher")
    public void removeTeacher(@RequestParam("subject-id") int subjectId) throws ServiceException {
        subjectService.removeTeacher(subjectId);
    }

    @PutMapping("/change/teacher")
    @ResponseStatus(HttpStatus.OK)
    public void changeTeacher(@RequestParam("subject-id") int subjectId, @RequestParam("teacher-id") int teacherId) throws ServiceException {
        subjectService.changeTeacher(subjectId, teacherId);
    }

    @GetMapping("/related/{audience-id}")
    public List<Subject> showSubjectsRelatedToAudience(@PathVariable("audience-id") int audienceId) throws ServiceException {
        return subjectService.findAllSubjectRelatedToAudience(audienceId);
    }

    @DeleteMapping("/{subject-id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeSubject(@PathVariable("subject-id") int subjectId) throws ServiceException {
        subjectService.removeById(subjectId);
    }
}
