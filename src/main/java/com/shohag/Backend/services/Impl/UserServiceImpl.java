package com.shohag.Backend.services.Impl;

import com.shohag.Backend.dtos.UserDto;
import com.shohag.Backend.entities.User;
import com.shohag.Backend.repositories.UserRepo;
import com.shohag.Backend.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User createdUser = this.userRepo.save(user);
        return this.userToDto(createdUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        return null;
    }

    @Override
    public UserDto getUserById(Long userId) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    // we can convert dto->entity / entity->dto using modelMapper also
    private User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAbout(user.getAbout());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
