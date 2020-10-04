package org.bsuir.service.impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.Producer;
import org.bsuir.service.ProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;

    @Override
    public List<Producer> findAll() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/producers/all", Producer[].class)));
    }

    @Override
    public Producer findById(Long id) {
        return restTemplate.getForObject(url + "/producers/{id}",Producer.class,id);
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(url + "/producers/{id}",id);
    }

    @Override
    public Producer save(Producer producer) {
        return restTemplate.postForObject(url + "/producers",producer,Producer.class);
    }

    @Override
    public List<Producer> saveAll(List<Producer> producers) {
       return Arrays.asList(Objects.requireNonNull(restTemplate.postForObject(url + "/producers/save-all", producers, Producer[].class)));
    }

}
