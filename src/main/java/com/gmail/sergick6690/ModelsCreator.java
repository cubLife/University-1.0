package com.gmail.sergick6690;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.*;
import com.gmail.sergick6690.service.*;
import com.gmail.sergick6690.universityModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelsCreator {
    private FacultyService facultyService;
    private ScheduleService scheduleService;
    private TeacherService teacherService;
    private SubjectService subjectService;
    private AudienceService audienceService;
    private CathedraService cathedraService;
    private GroupService groupService;

    @Autowired
    public ModelsCreator(FacultyService facultyService, ScheduleService scheduleService, TeacherService teacherService,
                         SubjectService subjectService, AudienceService audienceService, CathedraService cathedraService,
                         GroupService groupService) {
        this.facultyService = facultyService;
        this.scheduleService = scheduleService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.audienceService = audienceService;
        this.cathedraService = cathedraService;
        this.groupService = groupService;
    }

    public Cathedra createNewCathedra(CathedraForm cathedraForm) throws ServiceException {
        Cathedra cathedra = new Cathedra();
        cathedra.setName(cathedraForm.getName());
        cathedra.setFaculty(facultyService.findById(cathedraForm.getFacultyId()));
        return cathedra;
    }

    public Teacher createNewTeacher(TeacherForm teacherForm) throws ServiceException {
        Schedule schedule = scheduleService.findById(teacherForm.getScheduleId());
        return Teacher.builder().firstName(teacherForm.getFirstName()).lastName(teacherForm.getLastName())
                .age(teacherForm.getAge()).sex(teacherForm.getSex()).degree(teacherForm.getDegree()).schedule(schedule).build();
    }

    public Subject createNewSubject(SubjectForm subjectForm) throws ServiceException {
        Teacher teacher = teacherService.findById(subjectForm.getTeacherId());
        Subject subject = new Subject();
        subject.setName(subjectForm.getName());
        subject.setDescription(subjectForm.getDescription());
        subject.setTeacher(teacher);
        return subject;
    }

    public Item createNewItem(ItemForm itemForm) throws ServiceException {
        Subject subject = subjectService.findById(itemForm.getSubjectId());
        Audience audience = audienceService.findById(itemForm.getAudienceId());
        Schedule schedule = scheduleService.findById(itemForm.getScheduleId());
        String day = itemForm.getDay();
        int hour = itemForm.getHour();
        int duration = itemForm.getDuration();
        return new Item(subject, day, hour, audience, duration, schedule);
    }

    public Group createNewGroup(GroupForm groupForm) throws ServiceException {
        Group group = new Group();
        group.setName(groupForm.getName());
        group.setSchedule(scheduleService.findById(groupForm.getScheduleId()));
        group.setCathedra(cathedraService.findById(groupForm.getCathedraId()));
        return group;
    }

    public Student createNewStudent(StudentForm studentForm) throws ServiceException {
        Group group = groupService.findById(studentForm.getGroupId());
        return Student.builder().firstName(studentForm.getFirstName()).lastNAme(studentForm.getLastName())
                .sex(studentForm.getSex()).age(studentForm.getAge()).course(studentForm.getCourse()).group(group).build();
    }
}
