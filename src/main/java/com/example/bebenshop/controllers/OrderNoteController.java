package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.OrderNoteConsumeDto;
import com.example.bebenshop.services.OrderNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/order")
public class OrderNoteController extends BaseController {
    private final OrderNoteService mOrderNoteService;


    @PostMapping("/{id}")
    public ResponseEntity<BaseResponseDto> addOrderNote(@PathVariable Long id, @RequestBody OrderNoteConsumeDto orderNoteConsumeDto) {
        return created(mOrderNoteService.addOrderNote(id, orderNoteConsumeDto), "Created data successful.");
    }
}
