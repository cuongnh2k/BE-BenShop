package com.example.bebenshop.services;

import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.dto.produces.OrderProduceDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {

    OrderProduceDto createOrder(OrderConsumeDto orderConsumeDto);

    OrderProduceDto cancelOrder(Long id);

    OrderProduceDto getOrderById(Long id);

    BaseListProduceDto<OrderProduceDto> searchOrder(
            String orderStatusEnum
            , Long orderId
            , Optional<Long> startTime
            , Optional<Long> endTime
            , Pageable pageable);

    Long totalRevenue(
            String orderStatusEnum
            , Long orderId
            , Optional<Long> startTime
            , Optional<Long> endTime);

    OrderProduceDto updateStatusAdmin(Long id, OrderStatusEnum orderStatusEnum);
}
