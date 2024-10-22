package com.wedsite.zuong2004.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wedsite.zuong2004.dto.request.ApiResponse;
import com.wedsite.zuong2004.dto.request.OrderRequest;
import com.wedsite.zuong2004.dto.request.OrderRequestUpdate;
import com.wedsite.zuong2004.dto.response.OrderResponse;
import com.wedsite.zuong2004.enity.Order;
import com.wedsite.zuong2004.service.OrderService;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<OrderResponse> getOrderById(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrder(id))
                .build();
    }

    @GetMapping("/user/{id}")
    ApiResponse<List<Order>> getOrdersByUserId(@PathVariable Long id) {
        return ApiResponse.<List<Order>>builder()
                .result(orderService.getOrdersByUserId(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<OrderResponse> updateOrder(@RequestBody @Valid OrderRequestUpdate request, @PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(request, id))
                .build();
    }

    @GetMapping
    ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrders())
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ApiResponse.<String>builder()
                .result("Deleted successfully")
                .build();
    }

}
