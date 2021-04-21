package com.gmail.sergick6690;

import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.service.ScheduleService;
import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.university.Schedule;
import com.gmail.sergick6690.university.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
TablesCreator creator = context.getBean(TablesCreator.class);
        GroupService groupService=context.getBean(GroupService.class);
        ScheduleService scheduleService = context.getBean(ScheduleService.class);
        CathedraService cathedraService=context.getBean(CathedraService.class);
        creator.createTables("Script.sql");
        scheduleService.add(new Schedule());
        cathedraService.add(new Cathedra());
        groupService.add(new Group("def",1,1));
        groupService.add(new Group("sss",1,1));
        StudentService service = context.getBean(StudentService.class);
        service.add(Student.builder().firstName("Halya").groupID(1).course(2).build());

        service.changeGroup(1,2);
        System.out.println(service.findById(1));
      //  service.assignGroup(1,2);
        service.changeGroup(1,2);

        service.removeFromGroup(1);
        service.changeCourse(1,4);
        System.out.println(service.findAll());
    }
}
