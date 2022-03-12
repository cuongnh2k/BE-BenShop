package com.example.bebenshop.controllers.basic;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/basic/category")
public class BasicCategoryController extends BaseController {

    private final CategoryService mCategoryService;

    @GetMapping
    public ResponseEntity<BaseResponseDto>  getCategoriesByName(@RequestParam(required = false) String name){
        return success(mCategoryService.getCategoriesByName(name), "Get data successful.");
    }
}
