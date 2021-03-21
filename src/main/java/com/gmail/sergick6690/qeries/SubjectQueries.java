package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class SubjectQueries {
    private PropertyLoader loader = new PropertyLoader("Queries/subjectQueries.properties");
    private String addSubject;
    private String findSubjectById;
    private String findAllSubjects;
    private String removeSubjectById;
    private String findAllSubjectRelatedToAudience;

    public SubjectQueries() {
        Properties properties = loader.loadProperty();
        addSubject = properties.getProperty("addSubject");
        findSubjectById = properties.getProperty("findSubjectById");
        findAllSubjects = properties.getProperty("findAllSubjects");
        removeSubjectById = properties.getProperty("removeSubjectById");
        findAllSubjectRelatedToAudience = properties.getProperty("findAllSubjectRelatedToAudience");
    }

    public String getAddSubject() {
        return addSubject;
    }

    public String getFindSubjectById() {
        return findSubjectById;
    }

    public String getFindAllSubjects() {
        return findAllSubjects;
    }

    public String getRemoveSubjectById() {
        return removeSubjectById;
    }

    public String getFindAllSubjectRelatedToAudience() {
        return findAllSubjectRelatedToAudience;
    }
}
