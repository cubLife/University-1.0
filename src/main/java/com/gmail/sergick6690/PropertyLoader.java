package com.gmail.sergick6690;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private String propertyPath;

    public PropertyLoader(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public Properties loadProperty() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(propertyPath);
        Properties properties = new Properties();
        try {
            assert stream != null;
            properties.load(stream);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
