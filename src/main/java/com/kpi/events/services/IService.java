package com.kpi.events.services;

import java.util.List;

public interface IService<T> {

    List<T> findAll(int size, int page);

    T save(T entity);

    T find(long id);

    T update(long id, T entity);

    void delete(long id);
}
