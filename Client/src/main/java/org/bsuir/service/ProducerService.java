package org.bsuir.service;

import org.bsuir.model.Producer;

import java.util.List;

public interface ProducerService {

    List<Producer> findAll();

    Producer findById(Long id);

    void delete(Long id);

    Producer save(Producer producer);

    List<Producer> saveAll(List<Producer> producers);
}
