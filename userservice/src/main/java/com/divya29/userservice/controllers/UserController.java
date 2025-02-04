package com.divya29.userservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.userservice.dtos.UserDTO;
import com.divya29.userservice.dtos.UserResponseDto;
import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.models.User;
import com.divya29.userservice.services.IUserService;
import com.divya29.userservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private IUserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) throws NotFoundException {
        User user = userService.getUser(id);
        return new ResponseEntity<>(UserResponseDto.fromUser(user), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws NotFoundException {
        User user = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(UserResponseDto.fromUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws NotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        List<User> users = userService.listUsers();
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(UserResponseDto::fromUser)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }



}
