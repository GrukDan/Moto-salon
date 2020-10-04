package org.bsuir.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsuir.dto.ProductDto;
import org.bsuir.model.Producer;
import org.bsuir.model.ProductType;
import org.bsuir.service.ProductTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product-types")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping(value = "/all")
    public ResponseEntity getAll(){
        log.info("GET ALL PRODUCT TYPES");
        return ResponseEntity.ok(productTypeService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        productTypeService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody ProductType productType){
        productTypeService.save(productType);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(productTypeService.findById(id).orElseThrow(NullPointerException::new));
    }

}
