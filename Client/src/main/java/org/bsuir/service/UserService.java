package org.bsuir.service;

import javafx.beans.Observable;
import javafx.util.Callback;
import org.bsuir.dto.UserDto;
import org.bsuir.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    boolean auth(String email,String password);

    List<UserDto> getAllDto();

    User save(User user);

    List<UserDto> saveAll(List<UserDto> userDtos);
}
