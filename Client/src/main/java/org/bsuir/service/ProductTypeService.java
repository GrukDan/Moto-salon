package org.bsuir.service;

import org.bsuir.model.ProductType;

import java.util.List;

public interface ProductTypeService {

    List<ProductType> findAll();

    ProductType findById(Long id);
}
