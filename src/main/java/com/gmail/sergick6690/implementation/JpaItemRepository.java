package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public class JpaItemRepository implements com.gmail.sergick6690.Repository.ItemRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/itemQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllItems";

    @Override
    public void add(Item item) throws RepositoryException{
        try {
            entityManager.persist(item);
        } catch (Exception e) {
            throw new RepositoryException("Can't add item - " + item, e);
        }
    }

    @Override
    public Item findById(int id) throws RepositoryException {
        try {
            Item item = entityManager.find(Item.class, id);
            if (item != null) {
                return item;
            } else throw new IllegalArgumentException("Item not found - " + id);
        } catch (Exception e) {
            throw new RepositoryException("Item not found - " + id, e);
        }
    }

    @Override
    public List<Item> findAll() throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Item.class).getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any items", e);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        try {
            Item item = findById(id);
            entityManager.remove(item);
        } catch (Exception e) {
            throw new RepositoryException("Can't remove item with id - " + id, e);
        }
    }
}