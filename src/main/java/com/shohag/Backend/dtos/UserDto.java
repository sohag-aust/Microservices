package com.shohag.Backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @JsonProperty("user_name")
    private String name;
    private String email;
    private String password;
    private String about;
}
