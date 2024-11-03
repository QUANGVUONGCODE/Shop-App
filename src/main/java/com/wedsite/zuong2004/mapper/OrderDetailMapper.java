package com.wedsite.zuong2004.mapper;

import org.mapstruct.MappingTarget;

import com.wedsite.zuong2004.dto.request.OrderDetailRequest;
import com.wedsite.zuong2004.dto.response.OrderDetailResponse;
import com.wedsite.zuong2004.enity.OrderDetail;

public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailRequest orderDetail);

    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    void updateOrderDetail(OrderDetailRequest request, @MappingTarget OrderDetail orderDetail);
}
