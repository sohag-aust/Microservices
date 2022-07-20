package com.shohag.Backend.payloads;

import lombok.Data;

@Data
public class JWTAuthRequest {
    private final String username;
    private final String password;
}
