package org.bsuir.service.Impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.dto.ProductDto;
import org.bsuir.dto.UserDto;
import org.bsuir.model.Product;
import org.bsuir.model.Role;
import org.bsuir.model.User;
import org.bsuir.passay.PasswordGeneratorService;
import org.bsuir.repository.RoleRepository;
import org.bsuir.repository.UserRepository;
import org.bsuir.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordGeneratorService passwordGeneratorService;

    @Override
    public boolean authenticate(String email, String password) {
        return userRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordGeneratorService.generatePassword(6));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getAllDto() {
        return createDtos(userRepository.findAll(), roleRepository.findAll());
    }

    @Override
    public List<UserDto> saveAll(List<UserDto> userDtos) {
        List<User> users;
        if(userDtos.isEmpty()){
            userRepository.deleteAllInBatch();
            return new ArrayList<>();
        }else {
            users = fromDto(userDtos);
            userRepository.deleteAllNotIn(userDtos.stream()
                    .map(UserDto::getIdUser)
                    .collect(Collectors.toList()));
        }
        return createDtos(userRepository.saveAll(users), roleRepository.findAll());
    }

    private List<UserDto> createDtos(List<User> users, List<Role> roles){
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            Role role = roles.stream()
                    .filter(role1 -> role1.getIdRole().equals(user.getRole()))
                    .findFirst().orElseThrow(NullPointerException::new);
            userDtos.add(UserDto.of(user,role));
        });
        return userDtos;
    }

    private List<User> fromDto(List<UserDto> userDtos){
        return userDtos.stream()
                .map(UserDto::createUser)
                .collect(Collectors.toList());
    }
}
