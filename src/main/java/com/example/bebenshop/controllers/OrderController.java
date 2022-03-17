package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.CodeConsumeDto;
import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.services.OrderDetailService;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/order")
public class OrderController extends BaseController {

    private final OrderService mOrderService;
    private final OrderDetailService mOrderDetailService;
    private final ConvertUtil mConvertUtil;

    @PostMapping("/add-to-cart")
    public ResponseEntity<BaseResponseDto> addToCart(@RequestBody OrderDetailConsumeDto orderDetailConsumeDto) {
        return created(mOrderService.addToCart(orderDetailConsumeDto), "Add to cart successfully");
    }

    @PatchMapping("/add-order")
    public ResponseEntity<BaseResponseDto> addOrder() throws MessagingException {
        return success(mOrderService.addOrder(), "Order Success");
    }

    @PatchMapping("/{id}/verification-order")
    public ResponseEntity<BaseResponseDto> verificationOrder(@PathVariable Long id, @RequestBody CodeConsumeDto code) {
        return success(mOrderService.verificationOrder(code.getCode(), id), "Order confirmation successful");
    }

    @PatchMapping("/{id}/cancel-order")
    public ResponseEntity<BaseResponseDto> cancelOrder(@PathVariable Long id) {
        return success(mOrderService.cancelOrder(id), "Canceled order successfully");
    }

    @DeleteMapping("/detail/{id}")
    public ResponseEntity<BaseResponseDto> deleteOrderDetail(@PathVariable Long id) {
        mOrderDetailService.deleteOrderDetail(id);
        return success("Delete product successfully");
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> searchOrder(
            @RequestParam(defaultValue = "0") Integer page
            , @RequestParam(defaultValue = "10") Integer size
            , @RequestParam(required = false) String sort
            , @RequestParam OrderStatusEnum status) {
        Pageable pageable = mConvertUtil.buildPageable(page, size, sort);
        return success(mOrderService.searchOrder(status, pageable), "Get data successful");
    }
}
