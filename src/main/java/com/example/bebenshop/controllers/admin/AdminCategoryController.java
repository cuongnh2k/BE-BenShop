package com.example.bebenshop.controllers.admin;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.dto.consumes.CategoryConsumeDto;
import com.example.bebenshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


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

    @PostMapping
    public ResponseEntity<BaseResponseDto> addCategory(@RequestBody CategoryConsumeDto categoryConsumeDto) {
        return created(mCategoryService.addCategory(categoryConsumeDto), "Create data successful.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponseDto> editById(@PathVariable Long id
            , @RequestBody HashMap<String, Object> map) {
        return success(mCategoryService.editById(id, map), "Edit successful");
    }
}
