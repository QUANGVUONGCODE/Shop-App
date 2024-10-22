package com.wedsite.zuong2004.service;

import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.dto.request.OrderDetailRequest;
import com.wedsite.zuong2004.dto.response.OrderDetailResponse;
import com.wedsite.zuong2004.enity.Order;
import com.wedsite.zuong2004.enity.OrderDetail;
import com.wedsite.zuong2004.enity.Product;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;
import com.wedsite.zuong2004.mapper.OrderDetailMapper;
import com.wedsite.zuong2004.repository.OrderDetailRepository;
import com.wedsite.zuong2004.repository.OrderRepository;
import com.wedsite.zuong2004.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderDetailMapper orderDetailMapper;

    public OrderDetailResponse createOrderDetail(OrderDetailRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(request);

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));

        orderDetailRepository.delete(orderDetail);
    }

    public List<OrderDetailResponse> getOrderDetailsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());

        return orderDetails.stream().map(orderDetailMapper::toOrderDetailResponse).toList();
    }

    public OrderDetailResponse updateOrderDetail(OrderDetailRequest request, Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));

        orderDetailMapper.updateOrderDetail(request, orderDetail);
        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }
}
