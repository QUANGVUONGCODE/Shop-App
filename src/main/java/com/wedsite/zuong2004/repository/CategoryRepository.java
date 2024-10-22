package com.wedsite.zuong2004.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wedsite.zuong2004.enity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
