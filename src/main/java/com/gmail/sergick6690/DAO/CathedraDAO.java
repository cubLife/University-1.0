package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Cathedra;

import java.util.List;

public interface CathedraDAO {
    public void addCathedra(Cathedra cathedra);

    public void removeCathedraById(int id);

    public Cathedra findCathedraById(int id);

    public List<Cathedra> findAllCathedras();


}
