package com.shohag.Backend.controllers;

import com.shohag.Backend.dtos.UserDto;
import com.shohag.Backend.payloads.ApiResponse;
import com.shohag.Backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<UserDto>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/saveFakeUser")
    public ResponseEntity<UserDto> createFakeUser(@RequestBody UserDto userDto) { // @Valid annotation enables hibernate validators
        System.out.println("==** Inside Fake-User Controller **==");
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<UserDto>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateFakeUser/{userId}")
    public ResponseEntity<UserDto> updateFakeUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        System.out.println("==** Wana Update User **==");
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }
}
