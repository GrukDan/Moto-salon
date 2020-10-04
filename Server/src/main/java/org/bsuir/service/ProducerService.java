package org.bsuir.service;

import org.bsuir.model.Producer;

import java.util.List;

public interface ProducerService extends CrudService<Producer,Long> {

    List<Producer> saveAll(List<Producer> producers);

}
