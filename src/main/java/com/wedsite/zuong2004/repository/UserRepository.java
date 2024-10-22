package com.wedsite.zuong2004.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wedsite.zuong2004.enity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
