package com.example.bebenshop.controllers.admin;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.enums.OrderStatusEnum;
import com.example.bebenshop.services.OrderService;
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/admin/order")
public class AdminOrderController extends BaseController {

    private final OrderService mOrderService;
    private final ConvertUtil mConvertUtil;

    @PatchMapping("/{id}/update-status")
    public ResponseEntity<BaseResponseDto> updateStatus(
            @PathVariable Long id, @RequestBody OrderConsumeDto orderConsumeDto) {
        return success(mOrderService.updateStatusAdmin(id, orderConsumeDto.getStatus())
                , "Update order status successful");
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> searchOrder(
            @RequestParam(defaultValue = "0") Integer page
            , @RequestParam(defaultValue = "10") Integer size
            , @RequestParam(required = false) String sort
            , @RequestParam(required = false) Optional<OrderStatusEnum> status
            , @RequestParam(required = false) Optional<Long> startTime,
            @RequestParam(required = false) Optional<Long> endTime) {
        return success(mOrderService.searchOrder(
                status
                , startTime
                , endTime
                , mConvertUtil.buildPageable(page, size, sort)), "Get data successful");
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<BaseResponseDto> totalRevenue(
            @RequestParam(required = false) Optional<OrderStatusEnum> status
            , @RequestParam(required = false) Optional<Long> startTime,
            @RequestParam(required = false) Optional<Long> endTime) {
        return success(mOrderService.totalRevenue(
                status
                , startTime
                , endTime), "Get data successful");
    }
}
