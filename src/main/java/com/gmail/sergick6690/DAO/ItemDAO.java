package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    public void addItem(Item item);

    public Item findItemById(int id) throws SQLException;

    public List<Item> findAllItems();

    public void removeItemsById(int id);
}
