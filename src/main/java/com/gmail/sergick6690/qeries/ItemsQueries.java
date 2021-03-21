package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class ItemsQueries {
    private PropertyLoader loader = new PropertyLoader("Queries/itemQueries.properties");
    private String addItem;
    private String findItemById;
    private String findAllItems;
    private String removeItemsById;

    public ItemsQueries() {
        Properties properties = loader.loadProperty();
        addItem = properties.getProperty("addItem");
        findItemById = properties.getProperty("findItemById");
        findAllItems = properties.getProperty("findAllItems");
        removeItemsById = properties.getProperty("removeItemsById");
    }

    public String getAddItem() {
        return addItem;
    }

    public String getFindItemById() {
        return findItemById;
    }

    public String getFindAllItems() {
        return findAllItems;
    }

    public String getRemoveItemsById() {
        return removeItemsById;
    }
}
