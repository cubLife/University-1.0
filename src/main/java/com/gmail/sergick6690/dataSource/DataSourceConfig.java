package com.gmail.sergick6690.dataSource;

import javax.naming.NamingException;
import javax.sql.DataSource;

public interface DataSourceConfig {

    public DataSource getDataSource() throws NamingException;
}
