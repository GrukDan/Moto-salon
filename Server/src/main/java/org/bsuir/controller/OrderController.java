package org.bsuir.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsuir.dto.OrdersDto;
import org.bsuir.model.Description;
import org.bsuir.model.Orders;
import org.bsuir.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/all")
    public ResponseEntity getAll(){
        log.info("GET ALL ORDERS");
        return ResponseEntity.ok(orderService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        orderService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody Orders orders){
        orderService.save(orders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.findById(id).orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/all-dto")
    public ResponseEntity getAllDto(){
        return ResponseEntity.ok(orderService.getAllDto());
    }

    @PostMapping(value = "/save-all")
    public ResponseEntity saveAll(@RequestBody List<OrdersDto> orderDtos){
        return ResponseEntity.ok(orderService.saveAllAndGetDtos(orderDtos));
    }

    @PostMapping(value = "/descriptions")
    public ResponseEntity save(@RequestBody Description description){
        return ResponseEntity.ok(orderService.saveDescription(description));
    }
}
