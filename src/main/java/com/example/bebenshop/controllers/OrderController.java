package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.services.OrderDetailNoteService;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/order")
public class OrderController extends BaseController {

    private final OrderService mOrderService;
    private final OrderDetailNoteService mOrderDetailNoteService;
    private final ConvertUtil mConvertUtil;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createOrder(@RequestBody OrderConsumeDto orderConsumeDto) {
        return created(mOrderService.createOrder(orderConsumeDto), "Create order successful");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> cancelOrder(@PathVariable Long id) {
        return success(mOrderService.cancelOrder(id), "Cancel order successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getOrderById(@PathVariable Long id) {
        return success(mOrderService.getOrderById(id), "Get data successful");
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> searchOrder(
            @RequestParam(defaultValue = "0") Integer page
            , @RequestParam(defaultValue = "10") Integer size
            , @RequestParam(required = false) String sort
            , @RequestParam OrderStatusEnum status) {
        return success(mOrderService.searchOrder(status, mConvertUtil.buildPageable(page, size, sort)), "Get data successful");
    }

    @PatchMapping("/detail/note/{id}")
    public ResponseEntity<BaseResponseDto> updateOrderNote(@PathVariable("id") Long id, @RequestBody OrderDetailNoteConsumeDto orderNoteConsumeDto) {
        return success(mOrderDetailNoteService.editOrderDetailNote(id, orderNoteConsumeDto), "Update order detail note successful");
    }
}
