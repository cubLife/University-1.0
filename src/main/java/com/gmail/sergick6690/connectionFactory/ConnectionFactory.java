package com.gmail.sergick6690.connectionFactory;

import com.gmail.sergick6690.PropertyLoader;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Properties;

@Component
public class ConnectionFactory {
    private final Properties properties = new PropertyLoader("dbName.properties").loadProperty();

    public Properties getDBProperty() throws SQLException {
        Properties properties = new Properties();
        String dbName = this.properties.getProperty("db.name");
        if (dbName.equals("postgreSQL")) {
            properties = new PropertyLoader("PosdgreSQL.properties").loadProperty();
        } else if (dbName.equals("h2SQL")) {
            properties = new PropertyLoader("H2DataBase.properties").loadProperty();
        }
        assert properties != null;
        return properties;
    }
}