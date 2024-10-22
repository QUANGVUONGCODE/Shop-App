package com.wedsite.zuong2004.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wedsite.zuong2004.enity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long id);
}
