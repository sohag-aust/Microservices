package com.shohag.Backend.dtos;

import com.shohag.Backend.entities.Role;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleDto {
    private Long id;
    private String name;

    public static RoleDto entityToDto(Role role) {
        return new RoleDto()
                .setId(role.getId())
                .setName(role.getRoleName());
    }
}
