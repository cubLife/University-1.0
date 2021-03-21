package com.gmail.sergick6690;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        TablesCreator creator= (TablesCreator) applicationContext.getBean("tablesCreator");
        //creator.createTables();
        AudienceDAO audienceDAO = (AudienceDAO) applicationContext.getBean("jdbcAudienceDAO");
        audienceDAO.addAudience(new Audience());
        SubjectDAO subjectDAO= (SubjectDAO) applicationContext.getBean("jdbcSubjectDAO");
        TeacherDAO teacherDAO= (TeacherDAO) applicationContext.getBean("jdbcTeacherDAO");
       // System.out.println(subjectDAO.findAllSubjectRelatedToAudience(1));
        System.out.println(teacherDAO.findAllTeachersWithEqualDegree("w"));
    }
}
