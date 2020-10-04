package org.bsuir.service.impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.ProductType;
import org.bsuir.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {


    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;

    @Override
    public List<ProductType> findAll() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/product-types/all", ProductType[].class)));
    }

    @Override
    public ProductType findById(Long id) {
        return restTemplate.getForObject(url + "/product-types" + id.toString(),ProductType.class);
    }
}
