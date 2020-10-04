package org.bsuir.service.Impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.ProductType;
import org.bsuir.repository.ProductTypeRepository;
import org.bsuir.service.ProductTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Override
    public Optional<ProductType> findById(Long id) {
        return productTypeRepository.findById(id);
    }

    @Override
    public ProductType save(ProductType entity) {
        return productTypeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        productTypeRepository.deleteById(id);
    }

    @Override
    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }
}
