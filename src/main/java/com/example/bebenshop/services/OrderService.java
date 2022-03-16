package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailProduceDto;

public interface OrderService {

    OrderDetailProduceDto addToCart(OrderDetailConsumeDto orderDetailConsumeDto);
}
