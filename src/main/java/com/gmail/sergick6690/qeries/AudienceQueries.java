package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class AudienceQueries {
    private PropertyLoader loader = new PropertyLoader("Queries/audienceQueries.properties");
    private String addAudience;
    private String findAudienceById;
    private String findAllAudience;
    private String removeAudience;

    public AudienceQueries() {
        Properties properties = loader.loadProperty();
        addAudience = properties.getProperty("addAudience");
        findAudienceById = properties.getProperty("findAudienceById");
        findAllAudience = properties.getProperty("findAllAudience");
        removeAudience = properties.getProperty("removeAudience");
    }

    public String getAddAudience() {
        return addAudience;
    }

    public String getFindAudienceById() {
        return findAudienceById;
    }

    public String getFindAllAudience() {
        return findAllAudience;
    }

    public String getRemoveAudience() {
        return removeAudience;
    }
}