package org.bsuir.service.impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.Order;
import org.bsuir.model.OrderStatus;
import org.bsuir.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;

    @Override
    public List<OrderStatus> findAll() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/order-statuses/all", OrderStatus[].class)));
    }

    @Override
    public OrderStatus findById(Long id) {
        return null;
    }
}
