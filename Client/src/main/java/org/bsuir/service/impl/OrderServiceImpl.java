package org.bsuir.service.impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.dto.OrderDto;
import org.bsuir.dto.UserDto;
import org.bsuir.model.Description;
import org.bsuir.model.Order;
import org.bsuir.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;

    @Override
    public List<Order> findAll() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/orders/all", Order[].class)));
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {    }

    @Override
    public Order save(Order order) {
        return restTemplate.postForObject(url + "/orders",order,Order.class);
    }

    @Override
    public List<OrderDto> saveAll(List<OrderDto> orders) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.postForObject(url + "/orders/save-all", orders, OrderDto[].class)));

    }

    @Override
    public List<OrderDto> getAllDtos() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/orders/all-dto", OrderDto[].class)));
    }

    @Override
    public Description saveDescription(Description description) {
        return restTemplate.postForObject(url + "/orders/descriptions",description,Description.class);
    }
}
