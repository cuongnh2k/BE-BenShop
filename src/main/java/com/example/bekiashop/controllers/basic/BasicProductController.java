package com.example.bekiashop.controllers.basic;

import com.example.bekiashop.bases.BaseController;
import com.example.bekiashop.bases.BaseResponseDto;
import com.example.bekiashop.services.ProductCommentService;
import com.example.bekiashop.services.ProductService;
import com.example.bekiashop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/basic/product")
public class BasicProductController extends BaseController {

    private final ProductService mProductService;
    private final ConvertUtil mConvertUtil;
    private final ProductCommentService mProductCommentService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getProductById(@PathVariable Long id) {
        return success(mProductService.getProductById(id), "Get data successful");
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> searchByTitleOrDescription(
            @RequestParam(defaultValue = "0") Integer page
            , @RequestParam(defaultValue = "10") Integer size
            , @RequestParam(required = false) String sort
            , @RequestParam(defaultValue = "-1") String search
            , @RequestParam(defaultValue = "-1") BigDecimal priceMin
            , @RequestParam(defaultValue = "-1") BigDecimal priceMax
            , @RequestParam(defaultValue = "-1") String categoryId
            , @RequestParam(defaultValue = "-1") Long productId) {
        Pageable pageable = mConvertUtil.buildPageable(page, size, sort);
        return success(mProductService.searchByTitleOrDescription(
                        search
                        , priceMin
                        , priceMax
                        , categoryId
                        , productId
                        , pageable)
                , "Get data successful");
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<BaseResponseDto> getCommentByProductId(
            @PathVariable("id") Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String sort) {
        Pageable pageable = mConvertUtil.buildPageable(page, size, sort);
        return success(mProductCommentService.getCommentByProductId(id, pageable), "Get data successful");
    }

    @GetMapping("/{id}/category")
    public ResponseEntity<BaseResponseDto> searchProductByProductId(
            @RequestParam(defaultValue = "0") Integer page
            , @RequestParam(required = false) String sort
            , @RequestParam(defaultValue = "10") Integer size
            , @PathVariable Long id) {
        Pageable pageable = mConvertUtil.buildPageable(page, size, sort);
        return success(mProductService.searchProductByProductId(id, pageable), "Get data successful");
    }
}
