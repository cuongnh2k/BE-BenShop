package com.example.bebenshop.controllers.admin;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.ProductConsumeDto;
import com.example.bebenshop.services.ProductImageService;
import com.example.bebenshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/admin/product")
public class AdminProductController extends BaseController {

    private final ProductService mProductService;
    private final ProductImageService mProductImageService;


    @PostMapping
    public ResponseEntity<BaseResponseDto> createProduct(@RequestBody ProductConsumeDto productConsumeDto) {
        return created(mProductService.createProduct(productConsumeDto), "Created data successful.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponseDto> editProduct( @PathVariable Long id
            , @RequestBody HashMap<String, Object> map) {
        return success(mProductService.editProduct(id, map), "Edit successful");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteProductByID(@PathVariable Long id) {
        mProductService.deleteProductByID(id);
        return success("Delete data successful.");
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<BaseResponseDto> addProductImage(@PathVariable Long id, @RequestParam MultipartFile image) throws
            IOException {
        return created(mProductImageService.addProductImage(id, image), "Created data successful.");
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<BaseResponseDto> deleteProductImage(@PathVariable Long id) {
        mProductImageService.deleteProductImage(id);
        return success("Delete data successful.");
    }


}
