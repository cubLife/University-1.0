package com.gmail.sergick6690;

import com.gmail.sergick6690.connectionFactory.ConnectionFactory;
import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import com.gmail.sergick6690.implementation.JdbcSubjectDAO;
import com.gmail.sergick6690.implementation.JdbcTeacherDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan("com.gmail.sergick6690")
public class SpringConfig  {
    private ConnectionFactory connectionFactory;
    private ApplicationContext context;

    @Autowired
    public SpringConfig(ConnectionFactory connectionFactory, AnnotationConfigApplicationContext context) {
        this.connectionFactory=connectionFactory;
        this.context=context;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        Properties properties = connectionFactory.getDBProperty();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws SQLException {
        return new JdbcTemplate(dataSource());
    }

}
