package com.wedsite.zuong2004.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wedsite.zuong2004.dto.request.ApiResponse;
import com.wedsite.zuong2004.dto.request.ProductImageRequest;
import com.wedsite.zuong2004.dto.request.ProductRequest;
import com.wedsite.zuong2004.dto.request.ProductRequestUpdate;
import com.wedsite.zuong2004.dto.response.ProductListResponse;
import com.wedsite.zuong2004.dto.response.ProductResponse;
import com.wedsite.zuong2004.enity.ProductImage;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;
import com.wedsite.zuong2004.service.ProductImageService;
import com.wedsite.zuong2004.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;
    ProductImageService productImageService;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.creatProduct(request))
                .build();
    }

    @GetMapping
    ApiResponse<ProductListResponse> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<ProductResponse> products = productService.getAllProduct(pageRequest);
        List<ProductResponse> productResponses = products.getContent();
        int totalPage = products.getNumber() + 1;
        return ApiResponse.<ProductListResponse>builder()
                .result(ProductListResponse.builder()
                        .products(productResponses)
                        .page(totalPage).build())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProductResponse> updateProduct(@RequestBody @Valid ProductRequestUpdate request,
            @PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        productImageService.deleteProductImage(id);
        return ApiResponse.<String>builder()
                .result("Deleted successfully")
                .build();
    }

    @PostMapping("uploads/{id}")
    public ApiResponse<?> uploadImages(@PathVariable Long id, @ModelAttribute("files") List<MultipartFile> files) {
        try {
            ProductResponse productexisting = productService.getProductById(id);
            files = files == null ? new ArrayList<>() : files;
            if (files.size() > ProductImage.MAXIMUM_IMAGES) {
                throw new AppException(ErrorCode.SIZE_IMAGES);
            }
            if (files.isEmpty() || files.stream().allMatch(file -> file.getSize() == 0)) {
                throw new AppException(ErrorCode.INVALID_IMAGES);
            }
            List<ProductImage> images = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    throw new AppException(ErrorCode.IMAGE_MAX_SIZE);
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new AppException(ErrorCode.INVALID_FILE);
                }
                String filename = storeFile(file);
                ProductImage productImage = productService.creatProductImage(
                        productexisting.getId(),
                        ProductImageRequest.builder()
                                .imageUrl(filename)
                                .build());
                images.add(productImage);
            }
            return ApiResponse.<List<ProductImage>>builder()
                    .result(images)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .result(e.getMessage())
                    .build();
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public String storeFile(MultipartFile file) throws IOException {

        if (!isImageFile(file) && file.getOriginalFilename() == null) {
            throw new IOException("File is not an image");
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
