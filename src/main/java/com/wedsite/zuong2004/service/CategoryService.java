package com.wedsite.zuong2004.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.dto.request.CategoryRequest;
import com.wedsite.zuong2004.dto.response.CategoryResponse;
import com.wedsite.zuong2004.enity.Category;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;
import com.wedsite.zuong2004.mapper.CategoryMapper;
import com.wedsite.zuong2004.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Category category = categoryMapper.mapToCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    public CategoryResponse updateCategory(CategoryRequest request, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        category.setName(request.getName());
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_ID);
        }
        categoryRepository.deleteById(id);
    }
}
