package com.example.bebenshop.services;

import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailProduceDto;
import com.example.bebenshop.dto.produces.OrderProduceDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;

public interface OrderService {

    OrderDetailProduceDto addToCart(OrderDetailConsumeDto orderDetailConsumeDto);

    OrderProduceDto addOrder() throws MessagingException;

    OrderProduceDto verificationOrder(String code, Long orderId);

    OrderProduceDto cancelOrder(Long id);

    BaseListProduceDto<OrderProduceDto> searchOrder(OrderStatusEnum orderStatusEnum, Pageable pageable);
}
