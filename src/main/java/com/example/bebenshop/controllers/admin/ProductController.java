package com.example.bebenshop.controllers.admin;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/admin/product")
public class ProductController extends BaseController {

    private final ProductService mProductService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createProduct(@RequestBody ProductConsumeDto productConsumeDto) {
        return created(mProductService.createProduct(productConsumeDto), "Created data successful.");
    }
}
