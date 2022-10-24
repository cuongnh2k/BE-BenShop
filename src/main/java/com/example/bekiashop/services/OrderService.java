package com.example.bekiashop.services;

import com.example.bekiashop.bases.BaseListProduceDto;
import com.example.bekiashop.dto.consumes.OrderConsumeDto;
import com.example.bekiashop.dto.produces.OrderProduceDto;
import com.example.bekiashop.enums.OrderStatusEnum;
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

    OrderProduceDto getById(Long id);

    Long totalRevenue(
            String orderStatusEnum
            , Long orderId
            , Optional<Long> startTime
            , Optional<Long> endTime);

    OrderProduceDto updateStatusAdmin(Long id, OrderStatusEnum orderStatusEnum);
}
