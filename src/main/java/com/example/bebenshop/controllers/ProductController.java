package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.ProductCommentConsumeDto;
import com.example.bebenshop.services.ProductCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user/product/")
public class
ProductController extends BaseController {
    private final ProductCommentService mProductCommentService;

    @PostMapping("{id}/comment")
    public ResponseEntity<BaseResponseDto> addProductComment(@PathVariable("id") Long id, @RequestBody ProductCommentConsumeDto productCommentConsumeDto) {
        return created(mProductCommentService.createProductComment(productCommentConsumeDto, id), "Create data successful.");
    }
}
