package com.example.bebenshop.services;

import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.dto.produces.OrderProduceDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderProduceDto createOrder(OrderConsumeDto orderConsumeDto);

    OrderProduceDto cancelOrder(Long id);

    BaseListProduceDto<OrderProduceDto> searchOrder(OrderStatusEnum orderStatusEnum, Pageable pageable);

    OrderProduceDto updateStatusAdmin(Long id, OrderStatusEnum orderStatusEnum);
}
