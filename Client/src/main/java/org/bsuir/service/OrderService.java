package org.bsuir.service;

import org.bsuir.dto.OrderDto;
import org.bsuir.model.Description;
import org.bsuir.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Long id);

    void delete(Long id);

    Order save(Order order);

    List<OrderDto> getAllDtos();

    List<OrderDto> saveAll(List<OrderDto> orders);

    Description saveDescription(Description description);
}
