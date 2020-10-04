package org.bsuir.service.Impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.OrderStatus;
import org.bsuir.repository.OrderStatusRepository;
import org.bsuir.service.OrderStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    @Override
    public Optional<OrderStatus> findById(Long id) {
        return orderStatusRepository.findById(id);
    }

    @Override
    public OrderStatus save(OrderStatus entity) {
        return orderStatusRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        orderStatusRepository.deleteById(id);
    }

    @Override
    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAll();
    }
}
