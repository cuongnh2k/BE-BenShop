package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.CodeConsumeDto;
import com.example.bebenshop.dto.consumes.OrderDetailConsumeDto;
import com.example.bebenshop.dto.consumes.OrderNoteConsumeDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.services.OrderDetailService;
import com.example.bebenshop.services.OrderNoteService;
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
    private final OrderNoteService mOrderNoteService;

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

    @PostMapping("/note/{id}")
    public ResponseEntity<BaseResponseDto> addOrderNote(@PathVariable Long id, @RequestBody OrderNoteConsumeDto orderNoteConsumeDto) {
        return created(mOrderNoteService.addOrderNote(id, orderNoteConsumeDto), "Create order note successful");
    }

    @PatchMapping("/note/{id}")
    public ResponseEntity<BaseResponseDto> updateOrderNote(@PathVariable("id") Long id, @RequestBody OrderNoteConsumeDto orderNoteConsumeDto) {
        return success(mOrderNoteService.editOrderNote(id, orderNoteConsumeDto), "Update order note successful");
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<BaseResponseDto> deleteOrderNote(@PathVariable("id") Long id) {
        mOrderNoteService.deleteOderNoteById(id);
        return success("Delete order note successful");
    }
}
