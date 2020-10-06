package org.bsuir.service.impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.dto.ProductDto;
import org.bsuir.model.Product;
import org.bsuir.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;

    @Override
    public List<Product> findAll() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/products/all", Product[].class)));
    }

    @Override
    public Product findById(Long id) {
        return restTemplate.getForObject(url + "/products/{id}",Product.class,id);
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(url + "/products/{id}",id);
    }

    @Override
    public Product save(Product product) {
        return restTemplate.postForObject(url + "/products",product,Product.class);
    }

    @Override
    public List<ProductDto> saveAll(List<ProductDto> productDtos) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.postForObject(url + "/products/save-all", productDtos, ProductDto[].class)));
    }

    @Override
    public List<ProductDto> getAllDto() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/products/all-dto", ProductDto[].class)));
    }
}
