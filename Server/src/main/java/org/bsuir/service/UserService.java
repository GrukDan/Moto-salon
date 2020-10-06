package org.bsuir.service;

import org.bsuir.dto.UserDto;
import org.bsuir.model.User;

import java.util.List;

public interface UserService extends CrudService<User,Long> {

    UserDto authenticate(String email,String password);

    List<UserDto> getAllDto();

    List<UserDto> saveAll(List<UserDto> userDtos);

    UserDto saveDto(UserDto userDto);
}
