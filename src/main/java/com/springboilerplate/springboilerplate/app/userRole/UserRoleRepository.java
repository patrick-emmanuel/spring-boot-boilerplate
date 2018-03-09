package com.springboilerplate.springboilerplate.app.userRole;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);
}
