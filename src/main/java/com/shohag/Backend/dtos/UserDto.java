package com.shohag.Backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shohag.Backend.entities.Role;
import com.shohag.Backend.entities.User;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
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

    private Set<RoleDto> roles = new HashSet<>();

    public static UserDto entityToDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setAbout(user.getAbout())
                .setRoles(user.getRoles()
                        .stream()
                        .map(RoleDto::entityToDto)
                        .collect(Collectors.toSet()));
    }
}
