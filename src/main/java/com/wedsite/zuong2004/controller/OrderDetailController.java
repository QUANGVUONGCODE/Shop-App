package com.wedsite.zuong2004.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wedsite.zuong2004.dto.request.ApiResponse;
import com.wedsite.zuong2004.dto.request.OrderDetailRequest;
import com.wedsite.zuong2004.dto.response.OrderDetailResponse;
import com.wedsite.zuong2004.service.OrderDetailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-details")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailController {
    OrderDetailService orderDetailService;

    @PostMapping
    ApiResponse<OrderDetailResponse> createdOrderDetail(@RequestBody @Valid OrderDetailRequest request) {
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderDetailService.createOrderDetail(request))
                .build();
    }

    @GetMapping("/orders/{orderId}")
    ApiResponse<List<OrderDetailResponse>> getAllOrderDetails(@PathVariable Long orderId) {
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .result(orderDetailService.getOrderDetailsByOrderId(orderId))
                .build();
    }
}
