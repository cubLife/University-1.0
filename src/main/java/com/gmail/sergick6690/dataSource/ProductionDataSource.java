package com.gmail.sergick6690.dataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.sql.DataSource;

@PropertySource("classpath:jndi.properties")
@Component
@Profile("!test")
public class ProductionDataSource implements DataSourceConfig {
    private Environment environment;
    private static final String POSTGRES = "jndi-postgreSQL";

    @Autowired
    public ProductionDataSource(Environment environment) {
        this.environment = environment;
    }

    @Override
    public DataSource getDataSource() throws NamingException {
        DataSource dataSource = (DataSource) new JndiTemplate().lookup(environment.getProperty(POSTGRES));
        return new DelegatingDataSource(dataSource);
    }
}
