package org.bsuir.service;

import org.bsuir.dto.UserDto;
import org.bsuir.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends CrudService<User,Long> {

    boolean authenticate(String email,String password);

    List<UserDto> getAllDto();

    List<UserDto> saveAll(List<UserDto> userDtos);
}
