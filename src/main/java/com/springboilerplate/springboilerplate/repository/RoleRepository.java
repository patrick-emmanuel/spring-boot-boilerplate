package com.springboilerplate.springboilerplate.repository;

import com.springboilerplate.springboilerplate.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
}
