package com.shohag.Backend.payloads;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private final String jwt;
}
