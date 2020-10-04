package org.bsuir.service.Impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.Producer;
import org.bsuir.repository.ProducerRepository;
import org.bsuir.service.ProducerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;

    @Override
    public Optional<Producer> findById(Long id) {
        return producerRepository.findById(id);
    }

    @Override
    public Producer save(Producer entity) {
        return producerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        producerRepository.deleteById(id);
    }

    @Override
    public List<Producer> findAll() {
        return producerRepository.findAll();
    }

    @Override
    public List<Producer> saveAll(List<Producer> producers) {
        if(producers.isEmpty()){
            producerRepository.deleteAllInBatch();
            return new ArrayList<>();
        }else producerRepository.deleteAllNotIn(producers.stream()
                .map(Producer::getIdProducer)
                .collect(Collectors.toList()));
        return producerRepository.saveAll(producers);
    }
}
