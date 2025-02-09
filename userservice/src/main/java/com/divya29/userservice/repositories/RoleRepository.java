package com.divya29.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.userservice.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
