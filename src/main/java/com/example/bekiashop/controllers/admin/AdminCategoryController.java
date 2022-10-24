package com.example.bekiashop.controllers.admin;

import com.example.bekiashop.bases.BaseController;
import com.example.bekiashop.bases.BaseResponseDto;
import com.example.bekiashop.dto.consumes.CategoryConsumeDto;
import com.example.bekiashop.services.CategoryService;
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
        return success("Delete category successful");
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> addCategory(@RequestBody CategoryConsumeDto categoryConsumeDto) {
        return created(mCategoryService.addCategory(categoryConsumeDto), "Create category successful");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponseDto> editById(@PathVariable Long id
            , @RequestBody HashMap<String, Object> map) {
        return success(mCategoryService.editById(id, map), "Update category successful");
    }
}
