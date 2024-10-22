package com.wedsite.zuong2004.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.dto.request.ProductImageRequest;
import com.wedsite.zuong2004.dto.request.ProductRequest;
import com.wedsite.zuong2004.dto.request.ProductRequestUpdate;

import com.wedsite.zuong2004.dto.response.ProductResponse;
import com.wedsite.zuong2004.enity.Category;
import com.wedsite.zuong2004.enity.Product;
import com.wedsite.zuong2004.enity.ProductImage;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;

import com.wedsite.zuong2004.mapper.ProductMapper;
import com.wedsite.zuong2004.repository.CategoryRepository;
import com.wedsite.zuong2004.repository.ProductImageRepository;
import com.wedsite.zuong2004.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;
    ProductMapper productMapper;

    public ProductResponse creatProduct(ProductRequest request) {
        Category existingCategory = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));

        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Product product = productMapper.mapToProduct(request);
        product.setCategory(existingCategory);
        product.onCreate();
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public ProductResponse getProductById(Long id) {
        return productMapper.toProductResponse(productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    public Page<ProductResponse> getAllProduct(PageRequest pageable) {
        return productRepository.findAll(pageable).map(productMapper::toProductResponse);
    }

    public ProductResponse updateProduct(ProductRequestUpdate request, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        productMapper.updateProduct(request, product);
        product.onUpdate();
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_ID);
        }
        productRepository.deleteById(id);
    }

    public ProductImage creatProductImage(Long productId, ProductImageRequest request) {
        Product productexisting = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));

        ProductImage productImage = ProductImage.builder()
                .product(productexisting)
                .imageUrl(request.getImageUrl())
                .build();

        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES) {
            throw new AppException(ErrorCode.SIZE_IMAGES);
        }

        return productImageRepository.save(productImage);
    }

}
