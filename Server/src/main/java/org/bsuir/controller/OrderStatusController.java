package org.bsuir.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsuir.model.OrderStatus;
import org.bsuir.service.OrderStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/order-statuses")
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping(value = "/all")
    public ResponseEntity getAll(){
        log.info("GET ALL ORDER STATUSES");
        return ResponseEntity.ok(orderStatusService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        orderStatusService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody OrderStatus orderStatus){
        orderStatusService.save(orderStatus);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderStatusService.findById(id).orElseThrow(NullPointerException::new));
    }
}
