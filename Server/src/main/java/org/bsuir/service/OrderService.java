package org.bsuir.service;

import org.bsuir.dto.OrdersDto;
import org.bsuir.model.Description;
import org.bsuir.model.Orders;

import java.util.List;

public interface OrderService extends CrudService<Orders,Long> {

    List<OrdersDto> getAllDto();

    List<OrdersDto> saveAllAndGetDtos(List<OrdersDto> orders);

    Description saveDescription(Description description);
}
