package com.example.bebenshop.controllers.admin;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/admin/category")
public class AdminCategoryController extends BaseController {

    private final CategoryService mCategoryService;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteById(@PathVariable Long id) {
        mCategoryService.deleteById(id);
        return success("Delete data successful.");
    }
}
