package com.gmail.sergick6690.connectionFactory;

import com.gmail.sergick6690.PropertyLoader;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ConnectionFactory {
    private final Properties properties = new PropertyLoader("dbName.properties").loadProperty();
    private static final String NAME = "db.name";
    private static final String POSTGRES = "postgreSQL";
    private static final String H2 = "h2SQL";
    private static final String POSTGRES_PATH = "PosdgreSQL.properties";
    private static final String H2_PATH = "H2DataBase.properties";

    public Properties getDBProperty() {
        Properties properties = new Properties();
        String dbName = this.properties.getProperty(NAME);
        if (dbName.equals(POSTGRES)) {
            properties = new PropertyLoader(POSTGRES_PATH).loadProperty();
        } else if (dbName.equals(H2)) {
            properties = new PropertyLoader(H2_PATH).loadProperty();
        }
        assert properties != null;
        return properties;
    }
}