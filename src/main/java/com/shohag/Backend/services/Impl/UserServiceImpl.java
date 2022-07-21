package com.shohag.Backend.services.Impl;

import com.shohag.Backend.constants.AppConstants;
import com.shohag.Backend.dtos.UserDto;
import com.shohag.Backend.entities.Role;
import com.shohag.Backend.entities.User;
import com.shohag.Backend.exceptions.ResourceNotFoundException;
import com.shohag.Backend.repositories.RoleRepo;
import com.shohag.Backend.repositories.UserRepo;
import com.shohag.Backend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User createdUser = this.userRepo.save(user);
        return this.userToDto(createdUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto register(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // encode password and set it
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        // role
        Role role = this.roleRepo.findByRoleName(AppConstants.PRIVILEGE_NORMAL_USER).orElseThrow(() -> new ResourceNotFoundException("Role", "RoleName: " + AppConstants.PRIVILEGE_NORMAL_USER));
        user.getRoles().add(role);

        user.setRoles(user.getRoles());
        User registeredUser = this.userRepo.save(user);

        return UserDto.entityToDto(registeredUser);
    }

    // we can convert dto->entity / entity->dto using modelMapper also
    private User dtoToUser(UserDto userDto) {
        /**
            User user = new User();
            user.setId(userDto.getId());
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setAbout(userDto.getAbout());
            user.setPassword(userDto.getPassword());
            return user;
        */

        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    private UserDto userToDto(User user) {
        /**
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setAbout(user.getAbout());
            userDto.setPassword(user.getPassword());
            return userDto;
        */

        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
