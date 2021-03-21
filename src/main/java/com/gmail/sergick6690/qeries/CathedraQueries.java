package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class CathedraQueries {
    private PropertyLoader loader=new PropertyLoader("Queries/cathedraQueries.properties");
    private String addCathedra;
    private String removeCathedraById;
    private String findCathedraById;
    private String findAllCathedras;

    public CathedraQueries(){
        Properties properties = loader.loadProperty();
        addCathedra=properties.getProperty("addCathedra");
        removeCathedraById= properties.getProperty("removeCathedra");
        findCathedraById=properties.getProperty("findCathedraByID");
        findAllCathedras=properties.getProperty("findAll");
    }

    public String getAddCathedra() {
        return addCathedra;
    }

    public String getRemoveCathedraById() {
        return removeCathedraById;
    }

    public String getFindCathedraById() {
        return findCathedraById;
    }

    public String getFindAllCathedras() {
        return findAllCathedras;
    }
}
