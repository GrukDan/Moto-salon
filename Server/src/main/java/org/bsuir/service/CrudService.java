package org.bsuir.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudService<T,PK extends Serializable> {

    Optional<T> findById(PK id);

    T save(T entity);

    void delete(PK id);

    List<T> findAll();
}
