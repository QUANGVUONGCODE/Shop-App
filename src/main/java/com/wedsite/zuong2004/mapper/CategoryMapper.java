package com.wedsite.zuong2004.mapper;

import org.mapstruct.Mapper;

import com.wedsite.zuong2004.dto.request.CategoryRequest;
import com.wedsite.zuong2004.dto.response.CategoryResponse;
import com.wedsite.zuong2004.enity.Category;

public interface CategoryMapper {
    Category mapToCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
