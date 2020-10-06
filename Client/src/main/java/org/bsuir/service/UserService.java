package org.bsuir.service;

import org.bsuir.dto.UserDto;
import org.bsuir.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    UserDto auth(String email,String password);

    List<UserDto> getAllDto();

    User save(User user);

    UserDto saveDto(UserDto user);

    List<UserDto> saveAll(List<UserDto> userDtos);
}
