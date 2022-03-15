package com.example.bebenshop.controllers;

import com.example.bebenshop.bases.BaseController;
import com.example.bebenshop.bases.BaseResponseDto;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base.api}/user")
public class UserController extends BaseController {

    private final UserService mUserService;

    @GetMapping
    public ResponseEntity<BaseResponseDto> getUserDetail() {
        return success(mUserService.getUserDetail(), "Get data successful.");
    }
}
