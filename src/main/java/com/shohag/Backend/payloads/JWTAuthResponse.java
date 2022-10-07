package com.shohag.Backend.payloads;

import com.shohag.Backend.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponse {
    private String jwt;
    private UserDto user;
}
