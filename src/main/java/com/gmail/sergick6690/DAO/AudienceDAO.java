package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Audience;

import java.util.List;

public interface AudienceDAO {
    public void addAudience(Audience audience);

    public Audience findAudienceById(int id);

    public List<Audience> findAllAudience();

    public void removeAudienceById(int id);
}
