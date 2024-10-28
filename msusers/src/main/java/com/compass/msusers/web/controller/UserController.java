package com.compass.msusers.web.controller;

import com.compass.msusers.web.dto.UserCreateDto;
import com.compass.msusers.entity.User;
import com.compass.msusers.entity.util.Address;
import com.compass.msusers.service.UserService;
import com.compass.msusers.web.dto.UserPasswordDto;
import com.compass.msusers.web.dto.UserResponseDto;
import com.compass.msusers.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(UserMapper.toListDto(users));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto) {
        User user = UserMapper.toUser(dto);
        Address add = userService.getAddressByZipCode(dto.getCep());
        user.setAddress(add);
        User newUser = userService.create(user);
        return new ResponseEntity<>(UserMapper.toDto(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordDto dto) {
        userService.editPassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.noContent().build();
    }

}
