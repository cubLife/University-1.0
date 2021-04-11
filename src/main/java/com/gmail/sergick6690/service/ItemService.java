package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.implementation.JdbcItemDAO;
import com.gmail.sergick6690.university.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements CrudMethods<Item> {
    private JdbcItemDAO itemDAO;

    @Autowired
    public ItemService(JdbcItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public void add(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        itemDAO.add(item);
    }

    @Override
    public Item findById(int id) {
        return itemDAO.findById(id);
    }

    @Override
    public List<Item> findAll() {
        return itemDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        itemDAO.removeById(id);
    }
}