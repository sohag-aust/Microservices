package com.shohag.Backend.controllers;

import com.shohag.Backend.dtos.UserDto;
import com.shohag.Backend.exceptions.ApiException;
import com.shohag.Backend.payloads.JWTAuthRequest;
import com.shohag.Backend.payloads.JWTAuthResponse;
import com.shohag.Backend.security.JWTTokenHelper;
import com.shohag.Backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JWTTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(JWTTokenHelper jwtTokenHelper, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody JWTAuthRequest jwtAuthRequest) {
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String jwtToken = this.jwtTokenHelper.generateToken(userDetails);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(jwtToken);
        return new ResponseEntity<JWTAuthResponse>(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException badCredentialsException) {
            System.out.println("==** Invalid Details **==");
            throw new ApiException("Invalid username or password");
        } catch (DisabledException disabledException) {
            System.out.println("==** User is Disable **==");
        }
    }

    @PostMapping("/register")
    private ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) { // @valid annotation enable validation in userDTO
        UserDto registeredUser = this.userService.register(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }
}
