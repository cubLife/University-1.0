package com.gmail.sergick6690;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import com.gmail.sergick6690.implementation.JdbcGroupDAO;
import com.gmail.sergick6690.university.Audience;
import com.gmail.sergick6690.university.Group;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
       AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        TablesCreator creator = applicationContext.getBean(TablesCreator.class);
        creator.createTables("Script.sql");
    }
}
