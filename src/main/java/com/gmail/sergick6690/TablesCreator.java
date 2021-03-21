package com.gmail.sergick6690;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import java.net.URISyntaxException;
import java.util.Objects;
//@Component
    public class TablesCreator {
    @Autowired
        private  JdbcTemplate jdbcTemplate;
    private String propertyPath;

    public TablesCreator(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public void createTables() throws IOException, URISyntaxException {
                URI scriptPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource(propertyPath)).toURI();
                String query = IOUtils.toString(new FileReader(new File(scriptPath)));
               jdbcTemplate.update(query);
    }
}
