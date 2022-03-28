package com.example.bebenshop.controllers.admin;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/admin/order")
public class AdminOrderController extends BaseController {

    private final OrderService mOrderService;

//    @PatchMapping("/{id}/update-status")
//    public ResponseEntity<BaseResponseDto> updateStatus(@PathVariable Long id, @RequestBody OrderConsumeDto orderConsumeDto) {
//        return success(mOrderService.updateStatusAdmin(id, orderConsumeDto.getStatus()), "Update order status successful");
//    }
}
