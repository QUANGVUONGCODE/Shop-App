package com.wedsite.zuong2004.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wedsite.zuong2004.enity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
}
