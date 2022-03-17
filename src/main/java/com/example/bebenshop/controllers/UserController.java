package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user")
public class UserController extends BaseController {

    private final UserService mUserService;

    @GetMapping
    public ResponseEntity<BaseResponseDto> getUserDetail() {
        return success(mUserService.getUserDetail(), "Get data successful.");
    }

    @PatchMapping
    public ResponseEntity<BaseResponseDto> editById(@RequestBody HashMap<String, Object> map){
        return success(mUserService.editById(map), "Edit data successful.");
    }
}
