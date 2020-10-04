package org.bsuir.service.Impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.dto.OrdersDto;
import org.bsuir.model.*;
import org.bsuir.repository.*;
import org.bsuir.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final ProductTypeRepository productTypeRepository;
    private final DescriptionRepository descriptionRepository;

    private void setCustomerInDto(OrdersDto orderDto, List<User> customers){
        User user = customers.stream().filter(customer -> customer.getIdUser().equals(orderDto.getCustomer())).findFirst().orElseThrow(NullPointerException::new);
        orderDto.setName(user.getName());
        orderDto.setSurname(user.getSurname());
    }

    private void setStatusInDto(OrdersDto orderDto, List<OrderStatus> orderStatuses){
        orderDto.setStatusName(orderStatuses.stream()
                .filter(orderStatus -> orderStatus.getIdStatus().equals(orderDto.getStatus()))
                .findFirst().orElseThrow(NullPointerException::new)
                .getStatus());
    }

    private void setProductInDto(OrdersDto orderDto, List<Product> products){
        Product product = products.stream()
                .filter(product1 -> product1.getIdProduct().equals(orderDto.getProduct()))
                .findFirst().orElseThrow(NullPointerException::new);
        orderDto.setProductName(product.getProductName());
        orderDto.setIdProductType(product.getProductType());
    }

    private void setProducerNameInDto(OrdersDto orderDto, List<Producer> producers){
        Producer producer = producers.stream()
                .filter(producer1 -> producer1.getIdProducer().equals(orderDto.getProducer()))
                .findFirst().orElseThrow(NullPointerException::new);
        orderDto.setProducerName(producer.getProducerName());
    }

    private void setProductTypeInDto(OrdersDto orderDto, List<ProductType> productTypes){
        orderDto.setProductTypeName(productTypes.stream()
                .filter(productType -> productType.getIdProductType().equals(orderDto.getIdProductType()))
                .findFirst().orElseThrow(NullPointerException::new)
                .getProductType());
    }

    private void setDescriptionInDto(OrdersDto orderDto, List<Description> descriptions){
        if(orderDto.getDescription()!=null)
        orderDto.setDescriptionData(descriptions.stream()
                .filter(description -> description.getIdDescription().equals(orderDto.getDescription()))
                .findFirst().orElseThrow(NullPointerException::new)
                .getDescription());
    }

    @Override
    public List<OrdersDto> getAllDto() {
        List<OrdersDto> orderDtos = new ArrayList<>();

        orderRepository.findAll().forEach(order -> {
            OrdersDto orderDto = new OrdersDto(order);
            setCustomerInDto(orderDto,userRepository.findAll());
            setStatusInDto(orderDto,orderStatusRepository.findAll());
            setProductInDto(orderDto,productRepository.findAll());
            setProductTypeInDto(orderDto,productTypeRepository.findAll());
            setDescriptionInDto(orderDto,descriptionRepository.findAll());
            setProducerNameInDto(orderDto, producerRepository.findAll());
            orderDtos.add(orderDto);
        });
        return orderDtos;
    }

    @Override
    public List<OrdersDto> saveAllAndGetDtos(List<OrdersDto> orders) {
        if(orders.isEmpty()){
            orderRepository.deleteAllInBatch();
            return new ArrayList<>();
        }
        orderRepository.deleteAllNotIn(orders.stream().map(OrdersDto::getIdOrder).collect(Collectors.toList()));
        orderRepository.saveAll(orders.stream().map(OrdersDto::createOrder).collect(Collectors.toList()));
        return getAllDto();
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Orders save(Orders entity) {
        //todo
        entity.setCustomer(userRepository.getOne(3L).getIdUser());
        return orderRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {  }

    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Description saveDescription(Description description) {
        return descriptionRepository.saveAndFlush(description);
    }
}
