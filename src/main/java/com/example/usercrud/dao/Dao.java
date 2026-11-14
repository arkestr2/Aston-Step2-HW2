package com.example.usercrud.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void save(T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    T update(T entity);
    void delete(T entity);
}
