package com.example.bebenshop.controllers.basic;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/basic/category")
public class BasicCategoryController extends BaseController {

    private final CategoryService mCategoryService;

    @GetMapping
    public ResponseEntity<BaseResponseDto> getAll(@RequestParam(required = false) Boolean structure) {
        if (structure == null) {
            structure = false;
        }
        return success(mCategoryService.getAll(structure), "Get data successful.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getById(@PathVariable Long id){
        return success(mCategoryService.getById(id), "Get successful");
    }
}
