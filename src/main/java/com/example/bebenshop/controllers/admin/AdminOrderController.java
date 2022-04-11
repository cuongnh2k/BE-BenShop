package com.example.bebenshop.controllers.admin;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bebenshop.services.OrderDetailNoteService;
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
    private final OrderDetailNoteService mOrderDetailNoteService;

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
            , @RequestParam(defaultValue = "-1") String status
            , @RequestParam(required = false) Optional<Long> startTime
            , @RequestParam(required = false) Optional<Long> endTime
            , @RequestParam(defaultValue = "-1") Long orderId) {
        return success(mOrderService.searchOrder(
                status
                , orderId
                , startTime
                , endTime
                , mConvertUtil.buildPageable(page, size, sort)), "Get data successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getById(@PathVariable Long id) {
        return success(mOrderService.getById(id), "Get data successful");
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<BaseResponseDto> totalRevenue(
            @RequestParam(defaultValue = "-1") String status
            , @RequestParam(required = false) Optional<Long> startTime
            , @RequestParam(required = false) Optional<Long> endTime
            , @RequestParam(defaultValue = "-1") Long orderId) {
        return success(mOrderService.totalRevenue(
                status
                , orderId
                , startTime
                , endTime), "Get data successful");
    }

    @PostMapping("/detail/{id}/note")
    public ResponseEntity<BaseResponseDto> addOrderDetailNote(
            @PathVariable Long id,
            @RequestBody OrderDetailNoteConsumeDto orderDetailNoteConsumeDto) {
        return created(mOrderDetailNoteService.addOrderDetailNote(id, orderDetailNoteConsumeDto),
                "create order detail note successful");
    }

    @DeleteMapping("/detail/note/{id}")
    public ResponseEntity<BaseResponseDto> addOrderDetailNote(@PathVariable Long id) {
        mOrderDetailNoteService.deleteOrderDetailNote(id);
        return success("delete order detail note successful");
    }
}
