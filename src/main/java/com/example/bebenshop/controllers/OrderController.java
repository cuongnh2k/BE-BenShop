package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/order")
public class OrderController extends BaseController {

    private final OrderService mOrderService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<BaseResponseDto> getUserDetail(@RequestBody OrderDetailConsumeDto orderDetailConsumeDto) {
        return created(mOrderService.addToCart(orderDetailConsumeDto), "Get data successful.");
    }
}
