package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.OrderNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/")
public class OrderNoteController extends BaseController {
    private final OrderNoteService mOrderNoteService;

    @DeleteMapping("order-note/{id}")
    public ResponseEntity<BaseResponseDto> deleteOrderNote(@PathVariable("id") Long id) {
        mOrderNoteService.deleteOderNoteById(id);
        return success("Delete data successful.");
    }
}
