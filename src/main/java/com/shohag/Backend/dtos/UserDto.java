package com.shohag.Backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    private Long id;

    @JsonProperty("user_name")
    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters")
    private String name;

    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password length should be in between 3 to 10 characters")
//    @Pattern(regexp = )
    private String password;

    @NotEmpty
    private String about;
}
