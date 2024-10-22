package com.wedsite.zuong2004.service;

import java.util.Date;

import java.util.List;
import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.dto.request.OrderRequest;
import com.wedsite.zuong2004.dto.request.OrderRequestUpdate;
import com.wedsite.zuong2004.dto.response.OrderResponse;
import com.wedsite.zuong2004.enity.Order;
import com.wedsite.zuong2004.enity.User;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;
import com.wedsite.zuong2004.mapper.OrderMapper;
import com.wedsite.zuong2004.repository.OrderRepository;
import com.wedsite.zuong2004.repository.UserRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserRepository userRepository;

    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.NOT_USER));

        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setOrderDate(new Date());
        LocalDate localDate = request.getShippingDate() == null ? LocalDate.now() : request.getShippingDate();
        if (localDate.isBefore(LocalDate.now())) {
            throw new AppException(ErrorCode.INVALID_DATE);
        }
        order.setShippingDate(localDate);
        order.setActive(true);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    public List<OrderResponse> getAllOrders() {
        User user = userRepository.findById(1L).orElseThrow(() -> new AppException(ErrorCode.NOT_USER));
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.INVALID_ID));
        return orderRepository.findByUserId(user.getId());
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_ID));
        return orderMapper.toOrderResponse(order);
    }

    public OrderResponse updateOrder(OrderRequestUpdate request, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_ID));
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.NOT_USER));

        orderMapper.updateOrder(request, order);
        order.setUser(user);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }
}
