package com.gmail.sergick6690;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private String propertyPath;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");

    public PropertyLoader(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public Properties loadProperty() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(propertyPath);
        Properties properties = new Properties();
        try {
            assert stream != null;
            properties.load(stream);
        } catch (IOException e) {
            ERROR.error("Can't load property", e);
            e.printStackTrace();
        }
        return properties;
    }
}
