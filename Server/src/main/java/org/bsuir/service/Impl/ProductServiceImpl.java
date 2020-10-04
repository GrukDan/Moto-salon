package org.bsuir.service.Impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.dto.ProductDto;
import org.bsuir.model.Producer;
import org.bsuir.model.Product;
import org.bsuir.model.ProductType;
import org.bsuir.repository.ProductRepository;
import org.bsuir.repository.ProductTypeRepository;
import org.bsuir.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDto> getAllDto() {
        return createDtos(productRepository.findAll(), productTypeRepository.findAll());
    }

    private List<ProductDto> createDtos(List<Product> products, List<ProductType> productTypes){
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(product -> {
            String productTypeName = productTypes.stream()
                    .filter(productType ->
                            productType.getIdProductType().equals(product.getProductType()))
                    .findFirst().orElseThrow(NullPointerException::new).getProductType();
            productDtos.add(ProductDto.of(product,productTypeName));
        });
        return productDtos;
    }

    @Override
    public List<ProductDto> saveAll(List<ProductDto> productDtos) {
        List<Product> products;
        if(productDtos.isEmpty()){
            productRepository.deleteAllInBatch();
            return new ArrayList<>();
        }else {
            products = fromDto(productDtos);
            productRepository.deleteAllNotIn(products.stream()
                    .map(Product::getIdProduct)
                    .collect(Collectors.toList()));
        }
        return createDtos(productRepository.saveAll(products), productTypeRepository.findAll());
    }

    private List<Product> fromDto(List<ProductDto> productDtos){
        return productDtos.stream()
                .map(ProductDto::createProduct)
                .collect(Collectors.toList());
    }
}
