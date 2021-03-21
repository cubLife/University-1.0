package com.gmail.sergick6690;

import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import com.gmail.sergick6690.implementation.JdbcSubjectDAO;
import com.gmail.sergick6690.implementation.JdbcTeacherDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.gmail.sergick6690")
public class SpringConfig {
    private  final ApplicationContext applicationContext;
    private PropertyLoader propertyLoader = new PropertyLoader("Queries/PosdgreSQL.properties");

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public DataSource dataSource(){
        Properties properties = propertyLoader.loadProperty();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean TablesCreator tablesCreator(){
        return new TablesCreator("Script.sql");
    }

    @Bean
    public JdbcAudienceDAO jdbcAudienceDAO(){
        return new JdbcAudienceDAO();
    }

    @Bean
    public JdbcSubjectDAO jdbcSubjectDAO(){
        return new JdbcSubjectDAO();
    }

    @Bean
    public JdbcTeacherDAO jdbcTeacherDAO(){
        return new JdbcTeacherDAO();
    }
}
