package org.bsuir.service;

import org.bsuir.dto.ProductDto;
import org.bsuir.model.Producer;
import org.bsuir.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    void delete(Long id);

    Product save(Product product);

    List<ProductDto> saveAll(List<ProductDto> productDtos);

    List<ProductDto> getAllDto();
}
