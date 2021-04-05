package com.gmail.sergick6690.DAO;

import org.springframework.stereotype.Component;

import java.util.List;

public interface CRUD<T>{

        public void add(T obj);

        public T findById(int id) throws Exception;

        public List<T> findAll();

        public void removeById(int id);
}
