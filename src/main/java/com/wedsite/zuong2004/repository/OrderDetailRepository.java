package com.wedsite.zuong2004.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wedsite.zuong2004.enity.OrderDetail;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
}
