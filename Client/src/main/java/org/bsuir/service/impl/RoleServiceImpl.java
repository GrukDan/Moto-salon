package org.bsuir.service.impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.Role;
import org.bsuir.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;

    @Override
    public List<Role> findAll() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/roles/all", Role[].class)));
    }

    @Override
    public Role findById(Long id) {
        return restTemplate.getForObject(url + "/roles/" + id.toString(),Role.class);
    }
}
