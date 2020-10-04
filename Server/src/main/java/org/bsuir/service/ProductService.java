package org.bsuir.service;

import org.bsuir.dto.ProductDto;
import org.bsuir.model.Product;

import java.util.List;

public interface ProductService extends CrudService<Product,Long> {

    public List<ProductDto> getAllDto();

    List<ProductDto> saveAll(List<ProductDto> productDtos);
}
