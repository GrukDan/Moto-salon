package org.bsuir.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsuir.dto.ProductDto;
import org.bsuir.model.Product;
import org.bsuir.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/all")
    public ResponseEntity getAll(){
        log.info("GET ALL PRODUCTS");
        return ResponseEntity.ok(productService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        productService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody Product product){
        productService.save(product);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.findById(id).orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/all-dto")
    public ResponseEntity getAllDto(){
        log.info("GET ALL PRODUCT-DTOS");
        return ResponseEntity.ok(productService.getAllDto());
    }

    @PostMapping(value = "/save-all")
    public ResponseEntity saveAll(@RequestBody List<ProductDto> productDtos){
        log.info("SAVE PRODUCT-DTOS");
        return ResponseEntity.ok(productService.saveAll(productDtos));
    }


}
