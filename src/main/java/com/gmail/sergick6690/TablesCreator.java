package com.gmail.sergick6690;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
public class TablesCreator {
    private JdbcTemplate jdbcTemplate;
    private static final Logger INFO= LoggerFactory.getLogger("com.gmail.sergick6690.info" );

    @Autowired
    public TablesCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTables(String propertyPath) throws IOException, URISyntaxException {
        URI scriptPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource(propertyPath)).toURI();
        String query = IOUtils.toString(new FileReader(new File(scriptPath)));
        jdbcTemplate.update(query);
        INFO.info("Tables for University database was created");

    }
}