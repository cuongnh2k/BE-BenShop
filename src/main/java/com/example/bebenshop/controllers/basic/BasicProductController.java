package com.example.bebenshop.controllers.basic;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/basic/product")
public class BasicProductController extends BaseController {
    private final ProductService mProductService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getProductById(@PathVariable Long id) {
        return success(mProductService.getProductById(id),"Get data successful");
    }
}
