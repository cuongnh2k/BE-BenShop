package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.dto.produces.OrderProduceDto;

public interface OrderService {

    OrderProduceDto addToCart(OrderDetailConsumeDto orderDetailConsumeDto);
}
