package org.bsuir.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public UserDto auth(@RequestBody @Valid AuthRequest authRequest){
        log.info("Authentication: {}",authRequest.getEmail());
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
        log.info("SAVE USER: {}",user.getEmail());
        userService.save(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findById(id).orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/all-dto")
    public ResponseEntity getAllDto(){
        log.info("GET ALL USER-DTOS");
        return ResponseEntity.ok(userService.getAllDto());
    }

    @PostMapping(value = "/save-all")
    public ResponseEntity saveAll(@RequestBody List<UserDto> userDtos){
        log.info("SAVE ALL USERS");
        return ResponseEntity.ok(userService.saveAll(userDtos));
    }

    @PostMapping(value = "/dto")
    public ResponseEntity saveDto(@RequestBody UserDto userDto){
        log.info("UPDATE USER: {}",userDto.getEmail());
        return ResponseEntity.ok(userService.saveDto(userDto));
    }
}
