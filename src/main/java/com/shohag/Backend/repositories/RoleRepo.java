package com.shohag.Backend.repositories;

import com.shohag.Backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
