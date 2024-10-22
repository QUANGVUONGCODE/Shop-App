package com.wedsite.zuong2004.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.enity.ProductImage;
import com.wedsite.zuong2004.repository.ProductImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductImageService {
    ProductImageRepository productImageRepository;

    public void deleteProductImage(Long id) {
        List<ProductImage> productImages = productImageRepository.findByProductId(id);
        if (!productImages.isEmpty()) {
            productImageRepository.deleteAll(productImages);
        }
    }
}
