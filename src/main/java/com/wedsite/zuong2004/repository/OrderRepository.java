package com.wedsite.zuong2004.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.wedsite.zuong2004.enity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}