package com.gmail.sergick6690;

import com.gmail.sergick6690.exceptions.RepositoryException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
@Transactional(rollbackFor = RepositoryException.class)
public class TablesCreator {
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger INFO = LoggerFactory.getLogger("com.gmail.sergick6690.info");

    public void createTables(String propertyPath) throws IOException, URISyntaxException {
        URI scriptPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource(propertyPath)).toURI();
        String query = IOUtils.toString(new FileReader(new File(scriptPath)));
        entityManager.createNativeQuery(query).executeUpdate();
        INFO.info("Tables for University database was created");

    }
}