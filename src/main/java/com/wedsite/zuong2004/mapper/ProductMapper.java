package com.wedsite.zuong2004.mapper;

import org.mapstruct.MappingTarget;

import com.wedsite.zuong2004.dto.request.ProductRequest;
import com.wedsite.zuong2004.dto.request.ProductRequestUpdate;
import com.wedsite.zuong2004.dto.response.ProductResponse;
import com.wedsite.zuong2004.enity.Product;

public interface ProductMapper {
    Product mapToProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);

    void updateProduct(ProductRequestUpdate request, @MappingTarget Product product);
}
