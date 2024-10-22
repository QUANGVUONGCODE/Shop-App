package com.wedsite.zuong2004.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.wedsite.zuong2004.dto.request.OrderRequest;
import com.wedsite.zuong2004.dto.request.OrderRequestUpdate;
import com.wedsite.zuong2004.dto.response.OrderResponse;
import com.wedsite.zuong2004.enity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequest order);

    OrderResponse toOrderResponse(Order order);

    @Mappings({
            @Mapping(target = "orderDate", ignore = true)
    })
    void updateOrder(OrderRequestUpdate request, @MappingTarget Order order);
}
