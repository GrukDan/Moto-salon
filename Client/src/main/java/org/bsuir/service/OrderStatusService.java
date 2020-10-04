package org.bsuir.service;

import org.bsuir.model.OrderStatus;

import java.util.List;

public interface OrderStatusService {

    List<OrderStatus> findAll();

    OrderStatus findById(Long id);
}
