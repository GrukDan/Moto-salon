package org.bsuir.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsuir.dto.ProductDto;
import org.bsuir.dto.UserDto;
import org.bsuir.model.AuthRequest;
import org.bsuir.model.User;
import org.bsuir.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/auth")
    public Boolean auth(@RequestBody @Valid AuthRequest authRequest){
        log.info("Authentication:{}",authRequest.getEmail());
        return userService.authenticate(authRequest.getEmail(),authRequest.getPassword());
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAll(){
        log.info("GET ALL USERS");
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findById(id).orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/all-dto")
    public ResponseEntity getAllDto(){
        return ResponseEntity.ok(userService.getAllDto());
    }

    @PostMapping(value = "/save-all")
    public ResponseEntity saveAll(@RequestBody List<UserDto> userDtos){
        return ResponseEntity.ok(userService.saveAll(userDtos));
    }
}
