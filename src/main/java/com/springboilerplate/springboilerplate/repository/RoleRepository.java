package com.springboilerplate.springboilerplate.repository;

import com.springboilerplate.springboilerplate.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String name);
}
