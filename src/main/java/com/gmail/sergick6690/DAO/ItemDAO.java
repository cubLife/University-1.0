package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Item;

import java.util.List;

public interface ItemDAO {
    public void addItem(Item item);

    public Item findItemById(int id);

    public List<Item> findAllItems();

    public void removeItemsById(int id);
}
