package com.shohag.Backend.payloads;

import lombok.Data;

@Data
public class ApiResponse {
    private final String message;
    private final boolean isSuccess;
}
