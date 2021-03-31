package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Audience;

import java.util.List;

public interface AudienceDAO {
    public void addAudience(Audience audience);

    public Audience findAudienceById(int id) throws Exception;

    public List<Audience> findAllAudience();

    public void removeAudienceById(int id);
}
