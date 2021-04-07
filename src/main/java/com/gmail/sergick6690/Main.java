package com.gmail.sergick6690;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
       AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        TablesCreator creator = applicationContext.getBean(TablesCreator.class);
        creator.createTables("Script.sql");
    }
}
