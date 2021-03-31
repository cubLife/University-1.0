package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Cathedra;

import java.sql.SQLException;
import java.util.List;

public interface CathedraDAO {
    public void addCathedra(Cathedra cathedra);

    public void removeCathedraById(int id);

    public Cathedra findCathedraById(int id) throws SQLException;

    public List<Cathedra> findAllCathedras();


}
