package org.bsuir.service.impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.dto.ProductDto;
import org.bsuir.dto.UserDto;
import org.bsuir.model.AuthRequest;
import org.bsuir.model.Product;
import org.bsuir.model.User;
import org.bsuir.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:properties/application.properties")
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String url;

    @Override
    public List<User> findAll() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/users/all", User[].class)));
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public boolean auth(String email, String password) {
        return Objects.requireNonNull(
                restTemplate.postForObject(url + "/users/auth",new AuthRequest(email,password),boolean.class));
    }

    @Override
    public List<UserDto> getAllDto() {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(url + "/users/all-dto", UserDto[].class)));
    }

    @Override
    public User save(User user) {
        return restTemplate.postForObject(url + "/users",user, User.class);
    }

    @Override
    public List<UserDto> saveAll(List<UserDto> userDtos) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.postForObject(url + "/users/save-all", userDtos, UserDto[].class)));
    }
}
