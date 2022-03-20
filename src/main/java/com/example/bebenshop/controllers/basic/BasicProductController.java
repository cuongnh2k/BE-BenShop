package com.example.bebenshop.controllers.basic;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.ProductService;
import com.example.bebenshop.util.ConvertUtil;
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
            , @RequestParam(defaultValue = "-1") String categoryId) {
        Pageable pageable = mConvertUtil.buildPageable(page, size, sort);
        return success(mProductService.searchByTitleOrDescription(
                        search
                        , priceMin
                        , priceMax
                        , categoryId
                        , pageable)
                , "Get data successful");
    }
}
